package com.worthto.netty.handler3.codec;

/**
 * @author gezz
 * @description
 * @date 2020/3/7.
 */
public class ContentProtocol {
    private int length;

    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
