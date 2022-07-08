import java.util.*;
import java.util.HashMap;
import sailpoint.tools.Util;
import connector.common.JsonUtil;
import connector.common.Util;
import sailpoint.connector.webservices.WebServicesClient;
import sailpoint.connector.webservices.EndPoint;

Object personKey;
Object userName;
Object firstName;
Object lastName;
Object email;

Object fullName;
Object userId;
Object role;
Object startDate;
Object userStatus;
Object endDate;
Object shortName;
Object kronosJobPath;

int personKeyRef;

String authUserName = "Jon.Brown";
String authPassword = "N3wstreet@!";
String authClientId = "9D5I66wCQkuxoDaEzbItNyHXPsHqVvz0";
String authClientSecret = "2Hhfo5oqe1Sea2D5";
String authGrantType = "password";
String authAuthChain = "OAuthLdapService";

List < String > allowedStatuses = new ArrayList();
Map authHeader = new HashMap();
Map headers = new HashMap();
Map eventGridHeaders = new HashMap();
Map updatedMapInfo = new HashMap();
Map authBody = new HashMap();
List < String > FinalList = new ArrayList();
String authUrl = "https://specsavers-uat.npr.mykronos.com/api/authentication/access_token";
String baseUrl = "https://specsavers-uat.npr.mykronos.com/api/v1/commons/hyperfind/execute";
String personUrl = "https://specsavers-uat.npr.mykronos.com/api/v1/commons/persons/";
String eventGridUrl = "https://egtem4dwms011.westeurope-1.eventgrid.azure.net/api/events";
Map authArgs = new HashMap();
Map args = new HashMap();
Map eventGridArgs = new HashMap();
Map response = (Map) JsonUtil.toMap(rawResponseObject);
WebServicesClient restClient = new WebServicesClient();

allowedStatuses.add("2**");

authHeader.put("Content-Type", "application/x-www-form-urlencoded");
authHeader.put("appkey", "Ulk4IgM57FOTHjIEe8vmxxR0QwvWdeNt");
authBody.put("username", authUserName);
authBody.put("password", authPassword);
authBody.put("client_id", authClientId);
authBody.put("client_secret", authClientSecret);
authBody.put("grant_type", authGrantType);
authBody.put("auth_chain", authAuthChain);

authArgs.put(WebServicesClient.ARG_URL, authUrl);
restClient.configure(authArgs);

//throw new Exception(authUrl + "\n" + authBody + "\n" + authHeader + "\n" + authArgs);

Object auth = restClient.executePost(authUrl, authBody, authHeader, allowedStatuses);

Map authentication = (Map) JsonUtil.toMap(auth);

String authCode = authentication.get("access_token");

headers.put("Content-Type", "application/json");
headers.put("appkey", "Ulk4IgM57FOTHjIEe8vmxxR0QwvWdeNt");
headers.put("Authorization", authCode);

for (user: response.get("result").get("refs")) {

    personKeyRef = user.get("id");
    personUrlRef = personUrl + personKeyRef;

    args.put(WebServicesClient.ARG_URL, personUrlRef);
    restClient.configure(args);

    Object employees = restClient.executeGet(personUrlRef, headers, allowedStatuses);

    Map employee = (Map) JsonUtil.toMap(employees);

    try{
    personKey = employee.get("personIdentity").get("personKey");
    }
    catch(Exception e){
        personKey = "No Key";
    }
    try{
    userName = employee.get("user").get("userAccount").get("userName");
    }
    catch(Exception e){
        userName = "No Username";
    }
    try{
    firstName = employee.get("personInformation").get("person").get("firstName");
    }
    catch(Exception e){
        firstName = "No Firstname";
    }
    try{
    lastName = employee.get("personInformation").get("person").get("lastName");
    }
    catch(Exception e) {
        lastName = "No Last Name";
    }
    try{
    email = employee.get("personInformation").get("emailAddresses").get(0).get("address");
    }
    catch(Exception e){
        email = (String) "noemail@specsavers.com";
    }
    try{
    fullName = employee.get("personInformation").get("person").get("fullName");
    }
    catch(Exception e){
        fullName = "No Full Name";
    }

    try{
    userId = employee.get("personInformation").get("person").get("personNumber");
    }
    catch(Exception e){
        userId = "No User Id";
    }
    try{
    role = employee.get("gdapAssignments").get(0).get("role");
    }
    catch(Exception e){
        role = "No Role";
    }
    try{
    startDate = employee.get("personInformation").get("person").get("startDate");
    }
    catch(Exception e){
        startDate = "No Start Date";
    }
    try{
    userStatus = employee.get("personInformation").get("employmentStatusList").get(0).get("employmentStatusName");
    }
    catch(Exception e){
        userStatus = "No User Status";
    }
    try{
    endDate = employee.get("personInformation").get("employmentStatusList").get(0).get("expirationDate");
    }
    catch(Exception e) {
        endDate = "No End Date";
    }
    try{
    shortName = employee.get("personInformation").get("person").get("shortName");
    }
    catch(Exception e){
        shortName = "No Short Name";
    }
    try{
    kronosJobPath = employee.get("jobAssignment").get("primaryLaborAccounts").get(0).get("organizationPath");
    }
    catch(Exception e){
        kronosJobPath = "No Job Path";
    }
    


    eventGridArgs.put(WebServicesClient.ARG_URL, eventGridUrl);
    restClient.configure(eventGridArgs);
    eventGridHeaders.put("aeg-sas-key", "pLsKbRs9uMve3a2IgXaljFKlQlMk5ka81xOlS3TXxWM=");
    String eventGridPayload = "{\"UKG_Details\":{\"personKey\":" + personKey + "}, \"SF_Details\": {\"UserId\":" + userId + ",\"UPN\":" + userName + ",\"email\":" + email + ",\"FirstName\":" + firstName + ",\"LastName\":" + lastName + "}}";

    //restClient.executePost(eventGridUrl, eventGridPayload, eventGridHeaders, allowedStatuses);

    Map userMap = new HashMap();

userMap.put("email", email);
userMap.put("Person Key", personKey);
userMap.put("Username", userName);
userMap.put("firstname", firstName);
userMap.put("lastName", lastName);
userMap.put("fullName", fullName);
userMap.put("UserId", userId);
userMap.put("Role2", role);
userMap.put("startDate", startDate);
userMap.put("endDate", endDate);
userMap.put("userStatus", userStatus);
userMap.put("shortName", shortName);
userMap.put("kronosJobPath", kronosJobPath);

FinalList.add(userMap);

}

updatedMapInfo.put("data", FinalList);

return updatedMapInfo;