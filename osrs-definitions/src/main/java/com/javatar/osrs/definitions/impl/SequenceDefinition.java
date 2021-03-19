/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.javatar.osrs.definitions.impl;

import com.javatar.osrs.definitions.Definition;

public class SequenceDefinition implements Definition {
    private final int id;
    public int[] frameIDs; // top 16 bits are FrameDefinition ids
    public int[] chatFrameIds;
    public int[] frameLengths;
    public int[] frameSounds;
    public int frameStep = -1;
    public int[] interleaveLeave;
    public boolean stretches = false;
    public int forcedPriority = 5;
    public int leftHandItem = -1;
    public int rightHandItem = -1;
    public int maxLoops = 99;
    public int precedenceAnimating = -1;
    public int priority = -1;
    public int replyMode = 2;

    public SequenceDefinition(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public int[] getFrameIDs() {
        return frameIDs;
    }

    public void setFrameIDs(int[] frameIDs) {
        this.frameIDs = frameIDs;
    }

    public int[] getChatFrameIds() {
        return chatFrameIds;
    }

    public void setChatFrameIds(int[] chatFrameIds) {
        this.chatFrameIds = chatFrameIds;
    }

    public int[] getFrameLengths() {
        return frameLengths;
    }

    public void setFrameLengths(int[] frameLengths) {
        this.frameLengths = frameLengths;
    }

    public int[] getFrameSounds() {
        return frameSounds;
    }

    public void setFrameSounds(int[] frameSounds) {
        this.frameSounds = frameSounds;
    }

    public int getFrameStep() {
        return frameStep;
    }

    public void setFrameStep(int frameStep) {
        this.frameStep = frameStep;
    }

    public int[] getInterleaveLeave() {
        return interleaveLeave;
    }

    public void setInterleaveLeave(int[] interleaveLeave) {
        this.interleaveLeave = interleaveLeave;
    }

    public boolean isStretches() {
        return stretches;
    }

    public void setStretches(boolean stretches) {
        this.stretches = stretches;
    }

    public int getForcedPriority() {
        return forcedPriority;
    }

    public void setForcedPriority(int forcedPriority) {
        this.forcedPriority = forcedPriority;
    }

    public int getLeftHandItem() {
        return leftHandItem;
    }

    public void setLeftHandItem(int leftHandItem) {
        this.leftHandItem = leftHandItem;
    }

    public int getRightHandItem() {
        return rightHandItem;
    }

    public void setRightHandItem(int rightHandItem) {
        this.rightHandItem = rightHandItem;
    }

    public int getMaxLoops() {
        return maxLoops;
    }

    public void setMaxLoops(int maxLoops) {
        this.maxLoops = maxLoops;
    }

    public int getPrecedenceAnimating() {
        return precedenceAnimating;
    }

    public void setPrecedenceAnimating(int precedenceAnimating) {
        this.precedenceAnimating = precedenceAnimating;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getReplyMode() {
        return replyMode;
    }

    public void setReplyMode(int replyMode) {
        this.replyMode = replyMode;
    }

    @Override
    public int getDefinitionId() {
        return this.id;
    }
}
