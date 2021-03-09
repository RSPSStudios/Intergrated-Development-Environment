package com.javatar.saver;

import com.javatar.TrackDefinition;

import javax.sound.midi.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import static com.javatar.loader.TrackLoader.*;
import static javax.sound.midi.ShortMessage.*;


/**
 * Converts a MIDI file to the runescape format
 * <p>
 * NOTE: Jagex doesn't use the default soundbank, they have multiple soundbanks and their own instruments located in idx15 that use sound effects as their notes (idx4/14)
 * For this reason some midi files might sound different although most of their first soundbank matches the default soundbank instruments
 *
 * @author Vincent
 */

public class TrackSaver {
    private static void putVariableInt(DataOutputStream dos, int value) throws IOException {
        if ((value & ~0x7f) != 0) {
            if ((value & ~0x3fff) != 0) {
                if ((~0x1fffff & value) != 0) {
                    if ((~0xfffffff & value) != 0) {
                        dos.write(value >>> 28 | 0x80);
                    }
                    dos.write(value >>> 21 | 0x80);
                }
                dos.write(value >>> 14 | 0x80);
            }
            dos.write(value >>> 7 | 0x80);
        }
        dos.write(0x7f & value);
    }

    public byte[] serialize(File midiFile, TrackDefinition def) throws IOException, InvalidMidiDataException {
        Sequence sequence = MidiSystem.getSequence(midiFile);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(arrayOutputStream);

        //this could be done with a lot less loops and using multiple buffers instead

        //write event opcodes with channel
        for (Track track : sequence.getTracks()) {
            int prevChannel = 0;
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    int ch = (sm.getChannel() ^ prevChannel) << 4;
                    switch (sm.getCommand()) {
                        case NOTE_OFF -> {
                            dos.write(1 | ch);
                            prevChannel = sm.getChannel();
                        }
                        case NOTE_ON -> {
                            dos.write(ch);
                            prevChannel = sm.getChannel();
                        }
                        case KEY_AFTER_TOUCH -> {
                            dos.write(5 | ch);
                            prevChannel = sm.getChannel();
                        }
                        case CONTROL_CHANGE -> {
                            dos.write(2 | ch);
                            prevChannel = sm.getChannel();
                        }
                        case PROGRAM_CHANGE -> {
                            dos.write(6 | ch);
                            prevChannel = sm.getChannel();
                        }
                        case CHANNEL_AFTER_TOUCH -> {
                            dos.write(4 | ch);
                            prevChannel = sm.getChannel();
                        }
                        case PITCH_WHEEL_CHANGE -> {
                            dos.write(3 | ch);
                            prevChannel = sm.getChannel();
                        }
                    }
                } else if (message instanceof MetaMessage) {
                    MetaMessage mm = (MetaMessage) message;
                    switch (mm.getType()) {
                        case END_OF_TRACK:
                            dos.write(7);
                            break;
                        case SET_TEMPO:
                            dos.write(23);
                            break;
                        default:
                            //OTHER META EVENTS ARE IGNORED
                            break;
                    }
                } else {
                    //SYSEX MESSAGES ARE IGNORED
                }
            }

        }

        //write event timestamp for used opcodes
        for (Track track : sequence.getTracks()) {
            int lastTick = 0;
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    switch (sm.getCommand()) {
                        case NOTE_OFF:
                        case NOTE_ON:
                        case KEY_AFTER_TOUCH:
                        case CONTROL_CHANGE:
                        case PROGRAM_CHANGE:
                        case CHANNEL_AFTER_TOUCH:
                        case PITCH_WHEEL_CHANGE:
                            putVariableInt(dos, (int) event.getTick() - lastTick);
                            lastTick = (int) event.getTick();
                            break;
                    }
                } else if (message instanceof MetaMessage) {
                    MetaMessage mm = (MetaMessage) message;
                    switch (mm.getType()) {
                        case END_OF_TRACK:
                        case SET_TEMPO:
                            putVariableInt(dos, (int) event.getTick() - lastTick);
                            lastTick = (int) event.getTick();
                            break;
                    }
                }
            }
        }

        //jagex works with offset from the last one because this is usually 0 and gives better compression rates
        int lastController = 0;
        int lastNote = 0;
        int lastNoteOnVelocity = 0;
        int lastNoteOffVelocity = 0;
        int lastWheelChangeT = 0;
        int lastWheelChangeB = 0;
        int lastChannelAfterTouch = 0;
        int lastKeyAfterTouchVelocity = 0;

        //write controller number changes
        int[] lastControllerValue = new int[128];
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE) {
                        dos.write(sm.getData1() - lastController);
                        lastController = sm.getData1();
                    }
                }
            }
        }

        //controller 64 65 120 121 123 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && (sm.getData1() == 64 || sm.getData1() == 65 || sm.getData1() == 120 || sm.getData1() == 121 || sm.getData1() == 123)) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //key after touch velocity changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == KEY_AFTER_TOUCH) {
                        dos.write(sm.getData2() - lastKeyAfterTouchVelocity);
                        lastKeyAfterTouchVelocity = sm.getData2();
                    }
                }
            }
        }
        //channel after touch channel changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CHANNEL_AFTER_TOUCH) {
                        dos.write(sm.getData1() - lastChannelAfterTouch);
                        lastChannelAfterTouch = sm.getData1();
                    }
                }
            }
        }
        //pitch bend top values
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == PITCH_WHEEL_CHANGE) {
                        dos.write(sm.getData2() - lastWheelChangeT);
                        lastWheelChangeT = sm.getData2();
                    }
                }
            }
        }
        //controller 1 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 1) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //controller 7 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 7) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //controller 10 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 10) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //note changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_OFF || sm.getCommand() == NOTE_ON || sm.getCommand() == KEY_AFTER_TOUCH) {
                        dos.write(sm.getData1() - lastNote);
                        lastNote = sm.getData1();
                    }
                }
            }
        }
        //note on velocity changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_ON) {
                        dos.write(sm.getData2() - lastNoteOnVelocity);
                        lastNoteOnVelocity = sm.getData2();
                    }
                }
            }
        }
        //all unlisted controller changes (controllers are probably grouped like this because it gives an even better compression)
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && !(sm.getData1() == 64 || sm.getData1() == 65 || sm.getData1() == 120 || sm.getData1() == 121 || sm.getData1() == 123 || sm.getData1() == 0 || sm.getData1() == 32 || sm.getData1() == 1 || sm.getData1() == 33 || sm.getData1() == 7 || sm.getData1() == 39 || sm.getData1() == 10 || sm.getData1() == 42 || sm.getData1() == 99 || sm.getData1() == 98 || sm.getData1() == 101 || sm.getData1() == 100)) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //note off velocity changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_OFF) {
                        dos.write(sm.getData2() - lastNoteOffVelocity);
                        lastNoteOffVelocity = sm.getData2();
                    }
                }
            }
        }
        //controller 33 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 33) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //controller 39 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 39) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //controller 42 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 42) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //controller 0, 32 and program changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && (sm.getData1() == 0 || sm.getData1() == 32)) {
                        System.out.println("WARNING SONG USES SOUND BANKS BYTE: " + sm.getData1() + " VALUE: " + sm.getData2() + " ");
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    } else if (sm.getCommand() == PROGRAM_CHANGE) {
                        dos.write(sm.getData1());
                    }
                }
            }
        }
        //pitch bend bottom changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == PITCH_WHEEL_CHANGE) {
                        dos.write(sm.getData1() - lastWheelChangeB);
                        lastWheelChangeB = sm.getData1();
                    }
                }
            }
        }
        //controller 99 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 99) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //controller 98 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 98) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //controller 101 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 101) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //controller 100 changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == CONTROL_CHANGE && sm.getData1() == 100) {
                        dos.write(sm.getData2() - lastControllerValue[sm.getData1()]);
                        lastControllerValue[sm.getData1()] = sm.getData2();
                    }
                }
            }
        }
        //tempo changes
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage) {
                    MetaMessage mm = (MetaMessage) message;
                    if (mm.getType() == SET_TEMPO) {
                        dos.write(mm.getData());
                    }
                }
            }
        }
        //write footer
        dos.write(sequence.getTracks().length);
        dos.writeShort(sequence.getResolution());

        dos.flush();
        dos.close();
        return arrayOutputStream.toByteArray();
    }

}
