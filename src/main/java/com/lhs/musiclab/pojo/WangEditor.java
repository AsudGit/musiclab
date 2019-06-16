package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.util.Arrays;

public class WangEditor implements Serializable {
    private static final long serialVersionUID = -5378228583350890308L;

    private Integer errno; //错误代码，0 表示没有错误。
    private String[] data; //已上传的图片路径

    public WangEditor() {
    }

    public WangEditor(String[] data) {
        this.errno = 0;
        this.data = data;
    }

    public WangEditor(Integer errno, String[] data) {
        this.errno = errno;
        this.data = data;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WangEditor{" +
                "errno=" + errno +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
