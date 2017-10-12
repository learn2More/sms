package com.ppdai.ac.sms.consumer.core.model.bo;

/**
 * Created by kiekiyang on 2017/5/5.
 */
public class ProviderLine {
    private int line;

    private boolean chinaMobileLine;
    private boolean chinaUnicomLine;
    private boolean chinaTelecom;
    private boolean phs;
    private boolean allLine;

    public ProviderLine(int lineNumber) {
        this.line = lineNumber;
    }

    public boolean isAllLine() {
        if (this.line == 15)
            return true;
        return false;
    }

    //移动线路1000
    public boolean isChinaMobileLine() {
        int chinaMobile = (this.line >> 3) & 1;//this.line << 3;
        if (chinaMobile >= 1)
            return true;
        return false;
    }

    //联通线路0100
    public boolean isChinaUnicomLine() {
        int chinaUnicom = (this.line >> 2) & 1;//this.line << 2;
        if (chinaUnicom >= 1)
            return true;
        return false;
    }

    //电信线路0010
    public boolean isChinaTelecom() {
        int chinaTelecom = (this.line >> 1) & 1;//this.line << 1;
        if (chinaTelecom >= 1)
            return true;
        return false;
    }

    //小灵通线0001
    public boolean isPhs() {
        if (this.line == 1)
            return true;
        return false;
    }
}
