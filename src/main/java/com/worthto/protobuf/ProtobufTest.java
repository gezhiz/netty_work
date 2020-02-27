package com.worthto.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

/**
 * @author gezz
 * @description
 * @date 2020/2/27.
 */
public class ProtobufTest {

    @Test
    public void testStudent() throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder().setAge(20)
                .setName("gezz")
                .setAddress("北京市朝阳区")
                .build();
        byte[] studentBytes = student.toByteArray();
        DataInfo.Student student1 = DataInfo.Student.parseFrom(studentBytes);
        System.out.println(student1.getName());
        System.out.println(student1.getAddress());
        System.out.println(student1.getAge());
    }
}
