package FabLab.Backend;

import FabLab.Model.Machine;
import FabLab.Model.Material;
import FabLab.Model.User;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 18/04/2017.
 */
public class Backend
{
    public static void main(String[] args) {

        //getMachines();
        for(int i = 0; i < 200;i++){
        /*ArrayList<Machine> machines = getMachines();
        Machine machine = machines.get(3);
        HashMap<Material, Double> materials = new HashMap<>();
        machine.getMaterialList().forEach(m -> materials.put(m, 25.5));
        checkIn(user, machine, materials);
        System.out.println("Checkout: "+checkOut(machine));*/
        }
        User user = getUser("08F47175");
        for(Machine machine: user.getMachinesInUse())
            System.out.println(machine.getName());
        //checkIn(user, getMachines().get(0), new HashMap<>());
    }



    public static ArrayList<Machine> getMachines() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://fablab.klievan.be/api/machines");
        try {
            httpclient.execute(httpGet);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            String json = IOUtils.toString(response1.getEntity().getContent(), "UTF-8");
            JSONParser parser = new JSONParser();
            JSONArray jsonObject = (JSONArray) parser.parse(json);
            ArrayList<Machine> machines = machinesFromJson(jsonObject);
            httpclient.close();
            return machines;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkOut(Machine machine) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://fablab.klievan.be/api/machines/checkout/"+machine.getId());
        try {
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            String json = IOUtils.toString(response1.getEntity().getContent(), "UTF-8");
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            httpclient.close();
            System.out.println(jsonObject.get("result"));
            if(((String)jsonObject.get("result")).equalsIgnoreCase("success")) {
                return true;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkIn(User user, Machine machine, Map<Material, Double> materials) {
        JSONObject messageObject = new JSONObject();
        messageObject.put("user_id", user.getId());
        messageObject.put("machine_id", machine.getId());
        JSONObject materialObject = new JSONObject();
        for(Map.Entry<Material, Double> set: materials.entrySet()) {
            materialObject.put(set.getKey().getId(), set.getValue());
        }
        messageObject.put("materials", materialObject);
        System.out.println(messageObject.toString());
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://fablab.klievan.be/api/machines/checkin");
            httpPost.setHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json");
            StringEntity params =new StringEntity(messageObject.toJSONString());
            httpPost.setEntity(params);
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            String json = IOUtils.toString(response2.getEntity().getContent(), "UTF-8");
            JSONParser parser = new JSONParser();
            System.out.println(json+" test");
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            System.out.println(jsonObject);
            httpclient.close();
            if(jsonObject.get("result") != null && ((String) jsonObject.get("result")).equals("success")) {
                return true;
            } else {
                System.out.println(json);
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }

    public static User getUser(String nfc_uuid) {
        System.out.println();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://fablab.klievan.be/api/users/nfclookup");
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        httpPost.addHeader("Accept", "application/json");
        nvps.add(new BasicNameValuePair("nfc_uuid", nfc_uuid));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            String json = IOUtils.toString(response2.getEntity().getContent(), "UTF-8");
            System.out.println(json);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            if(jsonObject.get("result") != null && ((String) jsonObject.get("result")).equals("success")) {
                JSONObject userJson = (JSONObject) jsonObject.get("user");
                ArrayList<Machine> machinesInUse = machinesFromJson((JSONArray) userJson.get("machines"));
                User user = new User(((Long)userJson.get("id")).intValue(), (String) userJson.get("first_name"),(String)  userJson.get("last_name"),(String)  userJson.get("ua_id"),(String)  userJson.get("email"),(String)  userJson.get("department"),(String)  userJson.get("nfc_uuid"), machinesInUse);
                return user;
            } else {
                //TODO: maybe usernotfoundexception?
                System.out.println(json);
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static User createUser(String nfc_uuid, String firstName, String lastName, String department, String ua_id, String email) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://fablab.klievan.be/api/users/create");
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        httpPost.addHeader("Accept", "application/json");
        nvps.add(new BasicNameValuePair("nfc_uuid", nfc_uuid));
        nvps.add(new BasicNameValuePair("first_name", firstName));
        nvps.add(new BasicNameValuePair("last_name", lastName));
        nvps.add(new BasicNameValuePair("department", department));
        nvps.add(new BasicNameValuePair("email", email));
        System.out.println("Department: "+department);
        nvps.add(new BasicNameValuePair("ua_id", ua_id));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            String json = IOUtils.toString(response2.getEntity().getContent(), "UTF-8");
            JSONParser parser = new JSONParser();
            System.out.println("JSON: " + json);
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            if(jsonObject.get("result") != null && ((String) jsonObject.get("result")).equals("success")) {
                JSONObject userJson = (JSONObject) jsonObject.get("user");
                User user = new User(((Long)userJson.get("id")).intValue(), (String) userJson.get("first_name"),(String)  userJson.get("last_name"),(String)  userJson.get("ua_id"),(String)  userJson.get("email"),(String)  userJson.get("department"),(String)  userJson.get("nfc_uuid"), machinesFromJson((JSONArray) userJson.get("machines")));
                return user;
            } else {
                System.out.println(json);
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public static ArrayList<Machine> machinesFromJson(JSONArray jsonArray) {
        ArrayList<Machine> machines = new ArrayList<>();
        for(Object obj: jsonArray) {
            JSONObject jsonMachine = (JSONObject) obj;
            ArrayList<Material> materials = new ArrayList<>();
            for(Object materialObj: (JSONArray)jsonMachine.get("materials")) {
                JSONObject jsonMaterial = (JSONObject) materialObj;
                materials.add(new Material(((Long) jsonMaterial.get("id")).intValue(), (String) jsonMaterial.get("name"), (String) jsonMaterial.get("unit")));
            }
            Machine machine = new Machine(((Long)jsonMachine.get("id")).intValue(), (String)jsonMachine.get("name"), materials);
            if((boolean)jsonMachine.get("in_use")) {
                machine.setInUse(true);
                machine.setInUseBy((String) jsonMachine.get("in_use_by"));
            }
            System.out.println("Machine "+machine.hashCode()+" "+machine.getName()+" is in use by "+machine.getInUseBy());
            machines.add(machine);
        }
        return machines;
    }










}
