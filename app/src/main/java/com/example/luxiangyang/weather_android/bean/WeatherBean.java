package com.example.luxiangyang.weather_android.bean;


import java.util.List;

public class WeatherBean  {


    /**
     * code : 200
     * msg : 成功!
     * data : {"yesterday":{"date":"17日星期一","high":"高温 9℃","fx":"南风","low":"低温 -5℃","fl":"<![CDATA[<3级]]>","type":"晴"},"city":"海淀","aqi":null,"forecast":[{"date":"18日星期二","high":"高温 10℃","fengli":"<![CDATA[<3级]]>","low":"低温 -5℃","fengxiang":"西北风","type":"晴"},{"date":"19日星期三","high":"高温 10℃","fengli":"<![CDATA[<3级]]>","low":"低温 -4℃","fengxiang":"南风","type":"晴"},{"date":"20日星期四","high":"高温 5℃","fengli":"<![CDATA[<3级]]>","low":"低温 -5℃","fengxiang":"东南风","type":"多云"},{"date":"21日星期五","high":"高温 9℃","fengli":"<![CDATA[3-4级]]>","low":"低温 -4℃","fengxiang":"西北风","type":"多云"},{"date":"22日星期六","high":"高温 8℃","fengli":"<![CDATA[<3级]]>","low":"低温 -5℃","fengxiang":"北风","type":"多云"}],"ganmao":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。","wendu":"5"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * yesterday : {"date":"17日星期一","high":"高温 9℃","fx":"南风","low":"低温 -5℃","fl":"<![CDATA[<3级]]>","type":"晴"}
         * city : 海淀
         * aqi : null
         * forecast : [{"date":"18日星期二","high":"高温 10℃","fengli":"<![CDATA[<3级]]>","low":"低温 -5℃","fengxiang":"西北风","type":"晴"},{"date":"19日星期三","high":"高温 10℃","fengli":"<![CDATA[<3级]]>","low":"低温 -4℃","fengxiang":"南风","type":"晴"},{"date":"20日星期四","high":"高温 5℃","fengli":"<![CDATA[<3级]]>","low":"低温 -5℃","fengxiang":"东南风","type":"多云"},{"date":"21日星期五","high":"高温 9℃","fengli":"<![CDATA[3-4级]]>","low":"低温 -4℃","fengxiang":"西北风","type":"多云"},{"date":"22日星期六","high":"高温 8℃","fengli":"<![CDATA[<3级]]>","low":"低温 -5℃","fengxiang":"北风","type":"多云"}]
         * ganmao : 昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。
         * wendu : 5
         */

        private YesterdayBean yesterday;
        private String city;
        private Object aqi;
        private String ganmao;
        private String wendu;
        private List<ForecastBean> forecast;

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getAqi() {
            return aqi;
        }

        public void setAqi(Object aqi) {
            this.aqi = aqi;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 17日星期一
             * high : 高温 9℃
             * fx : 南风
             * low : 低温 -5℃
             * fl : <![CDATA[<3级]]>
             * type : 晴
             */

            private String date;
            private String high;
            private String fx;
            private String low;
            private String fl;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ForecastBean {
            /**
             * date : 18日星期二
             * high : 高温 10℃
             * fengli : <![CDATA[<3级]]>
             * low : 低温 -5℃
             * fengxiang : 西北风
             * type : 晴
             */

            private String date;
            private String high;
            private String fengli;
            private String low;
            private String fengxiang;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
