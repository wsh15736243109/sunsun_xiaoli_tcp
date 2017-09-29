package sunsun.xiaoli.jiarebang.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/12.
 */

public class PushModel implements Serializable{

    /**
     * msg_id : ul38778149456514167601
     * display_type : notification
     * random_min : 0
     * body : {"ticker":"设备昵称为: hehehH,编号为S0200000000002的加热棒通知","title":"设备昵称为: hehehH,编号为S0200000000002的加热棒通知","text":"时间:2017-05-12 04:58:21[UTC],内容:Abnormal heating rod temperature","play_vibrate":"true","play_lights":"true","play_sound":"true","after_open":"go_activity","activity":"00Q002001"}
     */

    private String msg_id;
    private String display_type;
    private int random_min;
    /**
     * ticker : 设备昵称为: hehehH,编号为S0200000000002的加热棒通知
     * title : 设备昵称为: hehehH,编号为S0200000000002的加热棒通知
     * text : 时间:2017-05-12 04:58:21[UTC],内容:Abnormal heating rod temperature
     * play_vibrate : true
     * play_lights : true
     * play_sound : true
     * after_open : go_activity
     * activity : 00Q002001
     */

    private BodyEntity body;

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public void setRandom_min(int random_min) {
        this.random_min = random_min;
    }

    public void setBody(BodyEntity body) {
        this.body = body;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public String getDisplay_type() {
        return display_type;
    }

    public int getRandom_min() {
        return random_min;
    }

    public BodyEntity getBody() {
        return body;
    }

    public static class BodyEntity implements Serializable{
        private String ticker;
        private String title;
        private String text;
        private String play_vibrate;
        private String play_lights;
        private String play_sound;
        private String after_open;
        private String activity;

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setPlay_vibrate(String play_vibrate) {
            this.play_vibrate = play_vibrate;
        }

        public void setPlay_lights(String play_lights) {
            this.play_lights = play_lights;
        }

        public void setPlay_sound(String play_sound) {
            this.play_sound = play_sound;
        }

        public void setAfter_open(String after_open) {
            this.after_open = after_open;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getTicker() {
            return ticker;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public String getPlay_vibrate() {
            return play_vibrate;
        }

        public String getPlay_lights() {
            return play_lights;
        }

        public String getPlay_sound() {
            return play_sound;
        }

        public String getAfter_open() {
            return after_open;
        }

        public String getActivity() {
            return activity;
        }
    }
}
