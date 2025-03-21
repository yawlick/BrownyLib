package ru.yawlick.brownylib.common.content.command;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.yawlick.brownylib.common.content.command.exception.NoArgumentException;

@Slf4j
@Getter @Setter
public class Args {
    private String[] args;

    public Args(String[] args, boolean canBeEmpty) {
        if(!canBeEmpty && args.length == 0)
            throw new NoArgumentException("Waited arguments is null!");
        this.args = args;
    }

    public Args(String[] args) {
        this.args = args;
    }

    /// -- ## Booleans ## --
    public boolean isEmpty() {
        if(args == null)
            return true;
        return args.length == 0;
    }

    public boolean isInt(int... integers) {
        if(isEmpty())
            return false;
        for(int i : integers) {
            if(!isInteger(get(i)))
                return false;
        }
        return true;
    }

    public boolean isDouble(int... integers) {
        if(isEmpty())
            return false;
        for(int i : integers) {
            if(!isDouble(get(i)))
                return false;
        }
        return true;
    }

    public boolean isFloat(int... integers) {
        if(isEmpty())
            return false;
        for(int i : integers) {
            if(!isFloat(get(i)))
                return false;
        }
        return true;
    }

    public boolean isLong(int... integers) {
        if(isEmpty())
            return false;
        for(int i : integers) {
            if(!isLong(get(i)))
                return false;
        }
        return true;
    }

    public boolean isShort(int... integers) {
        if(isEmpty())
            return false;
        for(int i : integers) {
            if(!isShort(get(i)))
                return false;
        }
        return true;
    }

    public boolean isByte(int... integers) {
        if(isEmpty())
            return false;
        for(int i : integers) {
            if(!isByte(get(i)))
                return false;
        }
        return true;
    }

    /// -- ## Getters / String ## --
    public String get(int num) {
        if(!isEmpty() && args.length >= num) {
            return args[(num - 1)];
        }
        return "";
    }

    public String first() {
        int num = 1;
        if(args.length >= num) {
            return args[(num - 1)];
        }
        return "";
    }

    public String second() {
        int num = 2;
        if(args.length >= num) {
            return args[(num - 1)];
        }
        return "";
    }

    public String third() {
        int num = 3;
        if(args.length >= num) {
            return args[(num - 1)];
        }
        return "";
    }

    public String fourth() {
        int num = 4;
        if(args.length >= num) {
            return args[(num - 1)];
        }
        return "";
    }

    public String fifth() {
        int num = 5;
        if(args.length >= num) {
            return args[(num - 1)];
        }
        return "";
    }

    /// -- ## Getters / Integer ## --
    public int getInt(int num) {
        if(!isEmpty() && args.length >= num) {
            return Integer.parseInt(args[(num - 1)]);
        }
        return -1;
    }

    public int firstInt() {
        int num = 1;
        if(args.length >= num) {
            return Integer.parseInt(args[(num - 1)]);
        }
        return -1;
    }

    public int secondInt() {
        int num = 2;
        if(args.length >= num) {
            return Integer.parseInt(args[(num - 1)]);
        }
        return -1;
    }

    public int thirdInt() {
        int num = 3;
        if(args.length >= num) {
            return Integer.parseInt(args[(num - 1)]);
        }
        return -1;
    }

    public int fourthInt() {
        int num = 4;
        if(args.length >= num) {
            return Integer.parseInt(args[(num - 1)]);
        }
        return -1;
    }

    public int fifthInt() {
        int num = 5;
        if(args.length >= num) {
            return Integer.parseInt(args[(num - 1)]);
        }
        return -1;
    }

    /// -- ## Getters / Double ## --
    public double getDouble(int num) {
        if(!isEmpty() && args.length >= num) {
            return Double.parseDouble(args[(num - 1)]);
        }
        return -1;
    }

    public double firstDouble() {
        int num = 1;
        if(args.length >= num) {
            return Double.parseDouble(args[(num - 1)]);
        }
        return -1;
    }

    public double secondDouble() {
        int num = 2;
        if(args.length >= num) {
            return Double.parseDouble(args[(num - 1)]);
        }
        return -1;
    }

    public double thirdDouble() {
        int num = 3;
        if(args.length >= num) {
            return Double.parseDouble(args[(num - 1)]);
        }
        return -1;
    }

    public double fourthDouble() {
        int num = 4;
        if(args.length >= num) {
            return Double.parseDouble(args[(num - 1)]);
        }
        return -1;
    }

    public double fifthDouble() {
        int num = 5;
        if(args.length >= num) {
            return Double.parseDouble(args[(num - 1)]);
        }
        return -1;
    }

    /// -- ## Utility ## --
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean isShort(String str) {
        try {
            Short.parseShort(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean isByte(String str) {
        try {
            Byte.parseByte(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}