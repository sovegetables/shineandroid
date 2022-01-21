package com.sovegetables.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GsonHelperTest {


    private static class Fruit{

        private String name;
        @SkipSerialize
        private String aliasName;
        @SerializedName("is_sweet")
        private boolean isSweet;
        @SerializedName("is_need_peeled")
        private int isNeedPeeled;
        private List<String> tastes;
        private int price;
        private String origin;

        public Fruit(String name) {
            this.name = name;
        }

        public Fruit(){
        }

        @Override
        public String toString() {
            return "Fruit{" +
                    "name='" + name + '\'' +
                    ", aliasName='" + aliasName + '\'' +
                    ", isSweet=" + isSweet +
                    ", isNeedPeeled=" + isNeedPeeled +
                    ", tastes=" + tastes +
                    ", price=" + price +
                    '}';
        }
    }

    @Test
    public void test_SkipSerialize(){
        Fruit fruit = new Fruit();
        fruit.name = "苹果";
        fruit.aliasName = "第一个水果";
        String json = GsonHelper.toJson(fruit);
        Assert.assertFalse(json.contains("aliasName"));
        String fromJson = "{\"name\":\"苹果\", \"aliasName\":\"水果之王\"}";
        Fruit fruit1 = GsonHelper.fromJson(fromJson, Fruit.class);
        Assert.assertNull(fruit1.aliasName);
    }

    @Test
    public void test_MistakeBooleanDeserializer(){
        String fromJson = "{\"name\":\"苹果\", \"is_sweet\":1}";
        Fruit newFruit = GsonHelper.fromJson(fromJson, Fruit.class);
        Assert.assertTrue(newFruit.isSweet);
        fromJson = "{\"name\":\"苹果\", \"is_sweet\":0}";
        newFruit = GsonHelper.fromJson(fromJson, Fruit.class);
        Assert.assertFalse(newFruit.isSweet);
        fromJson = "{\"name\":\"苹果\", \"is_sweet\":\"2\"}";
        newFruit = GsonHelper.fromJson(fromJson, Fruit.class);
        Assert.assertTrue(newFruit.isSweet);

        fromJson = "{\"name\":\"苹果\", \"is_sweet\":true}";
        newFruit = GsonHelper.commonGson().fromJson(fromJson, Fruit.class);
        Assert.assertTrue(newFruit.isSweet);
    }

    @Test
    public void test_MistakeIntDeserializer(){
        String fromJson = "{\"name\":\"苹果\", \"is_need_peeled\":True}";
        Fruit newFruit = GsonHelper.commonGson().fromJson(fromJson, Fruit.class);
        Assert.assertEquals(1, newFruit.isNeedPeeled);
        fromJson = "{\"name\":\"苹果\", \"is_need_peeled\":False}";
        newFruit = GsonHelper.fromJson(fromJson, Fruit.class);
        Assert.assertEquals(0, newFruit.isNeedPeeled);
        fromJson = "{\"name\":\"苹果\", \"is_need_peeled\":true}";
        newFruit = GsonHelper.fromJson(fromJson, Fruit.class);
        Assert.assertEquals(1, newFruit.isNeedPeeled);
        fromJson = "{\"name\":\"苹果\", \"is_need_peeled\":false}";
        newFruit = GsonHelper.commonGson().fromJson(fromJson, Fruit.class);
        Assert.assertEquals(0, newFruit.isNeedPeeled);


        fromJson = "{\"name\":\"苹果\", \"is_need_peeled\":2}";
        newFruit = GsonHelper.commonGson().fromJson(fromJson, Fruit.class);
        Assert.assertEquals(2, newFruit.isNeedPeeled);
    }


    @Test
    public void test_MistakeListDeserializer(){
        Fruit fruit = new Fruit();
        fruit.name = "苹果";
        ArrayList<String> tastes = new ArrayList<>();
        tastes.add("甜");
        tastes.add("脆");
        fruit.tastes = tastes;
        String json = GsonHelper.toJson(fruit);
        System.out.println(json);
        String fromJson = "{\"name\":\"苹果\",\"is_sweet\":false,\"is_need_peeled\":0,\"tastes\":\"\"}";
        checkList(fromJson);
        fromJson = "{\"name\":\"苹果\",\"is_sweet\":false,\"is_need_peeled\":0,\"tastes\":{}}";
        checkList(fromJson);
    }

    private void checkList(String fromJson) {
        Fruit newFruit = null;
        JsonSyntaxException exception = null;
        try {
            newFruit = new Gson().fromJson(fromJson, Fruit.class);
        } catch (JsonSyntaxException e) {
            exception = e;
        }
        Assert.assertNotNull(exception);
        newFruit = GsonHelper.fromJson(fromJson, Fruit.class);
        Assert.assertNotNull(newFruit.tastes);
    }

    @Test
    public void test_NotNullStringDeserializer(){
        String fromJson = "{\"name\":\"苹果\", \"origin\":null}";
        Fruit fruit = GsonHelper.cloneGsonBuilder()
                .registerTypeAdapter(String.class, new NotNullStringDeserializer())
                .create()
                .fromJson(fromJson, Fruit.class);
        Assert.assertNull(fruit.origin);
        fruit = new Gson().fromJson(fromJson, Fruit.class);
        Assert.assertNull(fruit.origin);
    }

    @Test
    public void test_double_to_int(){
       String fromJson = "{\"name\":\"苹果\", \"price\":2.88222}";
       Fruit fruit = GsonHelper.fromJson(fromJson, Fruit.class);
       System.out.println(fruit);
    }

    @Test
    public void test_(){
        String fromJson = "{\"videoId\":-32632392,\"videoTitle\":\"sit ut\",\"videoCoverUrl\":\"consectetur reprehenderit sint nostrud aliquip\",\"videoDesc\":\"Ut\",\"videoUrl\":\"ut dolore laboris sit\",\"videoDuration\":41525543,\"videoSource\":\"id pariatur quis non\",\"collectStatus\":true,\"likeStatus\":\"consectetur dolore nostrud\"}";
        VideoDetail detail = GsonHelper.cloneGsonBuilder().create().fromJson(fromJson, VideoDetail.class);
        Assert.assertNull(detail);
    }
}
