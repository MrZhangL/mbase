package com.tomcode.agent.interfaces;

public class Inherit {

    public static void main(String[] args) {
        PRecord pRecord = new PRecord("zs", 12);
        TRecord tRecord = new TRecord("ww", "Shanghai");
        Inter inter = new InterImpl();
        inter.display(tRecord);
    }

    interface Record{}

    static class PRecord implements Record {
        String name;
        int age;

        public PRecord(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    static class TRecord implements Record {
        String name;
        String addr;

        public TRecord(String name, String addr) {
            this.name = name;
            this.addr = addr;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return addr;
        }

        public void setAge(int age) {
            this.addr = addr;
        }
    }

    interface Inter<SOURCE extends Record> {
        void display(SOURCE source);
    }

    static class InterImpl implements Inter<PRecord> {

        @Override
        public void display(PRecord pRecord) {
            System.out.println(pRecord);
        }
    }


}
