import com.hrishikeshmishra.sb.Obj;

class SampleScript2 {

    String process(Obj obj) {
        if(obj.getMap().get("V") == 10)
            return "equal";
        else
            return "not-equal"
    }
}