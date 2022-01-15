package Project;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Politician {

    private String alter;
    private String name;
    private String geschlecht;
    private String aktiv;
    private String haare;
    private String partei;
    private String amt;
    private String brille;

    public static ArrayList<Politician> finalPoliticianList;
    public static String lastPolitician;
    public static ArrayList<String> categoryList;

    private static int probAlter;
    private static int probGeschlecht;
    private static int probAktiv;
    private static int probHaare;
    private static int probPartei;
    private static int probAmt;
    private static int probBrille;

    private ArrayList<String> probList;

    public static void fillList() {

        // Eine Methode, die den Politikern zugehörigen Kategorien in eine List speichert.
        // Die Liste wird in getProbability() abgearbeitet und wird dazu verwendet,
        // eine geeignete Frage den übrigen Personen entsprechend zu berechnen. (Möglichst nahe an 50:50)

        categoryList = new ArrayList<String>();
        categoryList.add("geschlecht");
        categoryList.add("alive");
        categoryList.add("aktiv");
        categoryList.add("haare");
        categoryList.add("partei");
        categoryList.add("amt");
        categoryList.add("brille");
    }

    public Politician(String name, String geschlecht, String alter, String aktiv, String haare, String partei, String amt, String brille) {

        // Ich bin ein Konstruktor

        this.name = name;
        this.geschlecht = geschlecht;
        this.alter = alter;
        this.aktiv = aktiv;
        this.haare = haare;
        this.partei = partei;
        this.amt = amt;
        this.brille = brille;
    }

    public static int getPoliticiansLeft() {

        return finalPoliticianList.size();
    }

    public static String getLastPolitician() {

        // wird am Ende des Spiels aufgerufen und gibt einfach den Namen der letzten Person aus

        lastPolitician = finalPoliticianList.get(0).name;
        return lastPolitician;
    }

    public String getCategory(String category) {

        // Wird verwendet um eine bestimmte Eigenschaft des aktuellen Objekts auszugeben

        switch (category) {
            case "name":
                return name;
            case "geschlecht":
                return geschlecht;
            case "alter":
                return alter;
            case "aktiv":
                return aktiv;
            case "haare":
                return haare;
            case "partei":
                return partei;
            case "amt":
                return amt;
            case "brille":
                return brille;
            default:
                return null;
        }
    }

    public Politician() {

        // Ich bin ein Konstruktor
    }

    public String getName() {
        return name;
    }

    public String getAktiv() {
        return aktiv;
    }

    public String getHaare() {
        return haare;
    }

    public String getAlter() {
        return alter;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public String getPartei() {
        return partei;
    }

    public String getAmt() {
        return amt;
    }

    public String getBrille() { return brille; }


    public static void setPoliticianList(String category, boolean answer, String answerString) {

        // Bearbeitet je nach Antwort die Liste der verbleibenden Politiker
        // Es wird anhand der Kategorie der aktuellen Frage nach Attributen gefiltert
        // und die Ergebnisse in eine neue Arraylist gespeichert

        if (answer) {
            List<Politician> weiblichePolitiker = Politician.getPoliticiansArr()
                    .stream()
                    .filter(Politician -> Politician.getCategory(category).equals(answerString))
                    .collect(Collectors.toList());
            finalPoliticianList = (ArrayList<Politician>) weiblichePolitiker;
        }

        else {
            List<Politician> weiblichePolitiker = Politician.getPoliticiansArr()
                    .stream()
                    .filter(Politician -> !Politician.getCategory(category).equals(answerString))
                    .collect(Collectors.toList());
            finalPoliticianList = (ArrayList<Politician>) weiblichePolitiker;
        }
    }

    public static String categoryOneAfterTheOther() {

        // Siehe auch Kommentar von fillList()
        // Die Methode arbeitet die Liste der Kategorien ab
        // und gibt eine nach der anderen aus.
        // Wird ebenfalls in Project.Politician.getProbability verwendet.

        String tempString;
        if (categoryList.size() == 0) {
            fillList();
            tempString = categoryList.get(0);
        }
        else {
            tempString = categoryList.get(0);
        }
        categoryList.remove(0);
        return tempString;
    }

    public static String mergeProbability() {

        // Sammelt aus den getProbability Methoden der einzelnen Kategorien alle Werte.
        // Die Frage, die am ehesten zu einem 50:50 Ausschluss führt wird ausgewählt.
        // In dieser Methode wird die Antwort (Attribut im JSON File) der gewählten Frage ausgegeben.
        // Über Question.stringToIndex wird der Index der Fragenliste gefunden und in Question.getNewQuestion
        // der zugehörige Fragenstring ausgegeben

        ArrayList<Integer> someList = new ArrayList<Integer>();
        ArrayList<String> anotherList = new ArrayList<String>();

        someList.add(probAlter);
        anotherList.add(getProbabilityAlter());
        someList.add(probGeschlecht);
        anotherList.add(getProbabilityGeschlecht());
        someList.add(probAktiv);
        anotherList.add(getProbabilityAktiv());
        someList.add(probHaare);
        anotherList.add(getProbabilityHaare());
        someList.add(probAmt);
        anotherList.add(getProbabilityAmt());
        someList.add(probBrille);
        anotherList.add(getProbabilityBrille());

        int tempInt = 0;
        String tempString = "";

        for (int i = 0; i < 6; i++) {
            if (someList.get(i) > tempInt && someList.get(i) != 0) {
                tempInt = someList.get(i);
                tempString = anotherList.get(i);
            }
        }
        return tempString;
    }

    public static String getProbabilityAlter() {

        // Teil eines Versuchs einer "intelligenten" Fragenfindung

        int limit = finalPoliticianList.size();;
        ArrayList<Politician> tempList = finalPoliticianList;
        ArrayList<Question> questionList = Question.getQuestionsArr();
        int someInt = 0;
        Question tempQuestion = new Question();

        String alter = "alter";
        // tot, jung, mittel, alt, uralt

        int totCount = 0;
        int jungCount = 0;
        int mittelCount = 0;
        int altCount = 0;
        int uraltCount = 0;

        for (int i = 0; i < limit; i++) {
            Politician tempPol = finalPoliticianList.get(i);

            alter = tempPol.alter;

            switch (alter) {
                case "tot":
                    totCount++;
                case "jung":
                    jungCount++;
                case "mittel":
                    mittelCount++;
                case "alt":
                    altCount++;
                case "uralt":
                    uraltCount++;
                default:
                    someInt++;
            }
        }

        int durchschnitt = (totCount + jungCount + mittelCount + altCount + uraltCount) / 5;
        ArrayList<Integer> someList = new ArrayList<Integer>();
        ArrayList<String> anotherList = new ArrayList<String>();

        someList.add(totCount);
        anotherList.add("tot");
        someList.add(jungCount);
        anotherList.add("jung");
        someList.add(mittelCount);
        anotherList.add("mittel");
        someList.add(altCount);
        anotherList.add("alt");
        someList.add(uraltCount);
        anotherList.add("uralt");

        int tempInt = 0;
        String tempString = "";

        for (int i = 0; i < 5; i++) {
            if (durchschnitt - someList.get(i) > tempInt && someList.get(i) != 0) {
                tempInt = someList.get(i);
                tempString = anotherList.get(i);
                probAlter = durchschnitt - someList.get(i);

            }
        }
        return tempString;
    }

    public static String getProbabilityBrille() {

        // Teil eines Versuchs einer "intelligenten" Fragenfindung

        int limit = finalPoliticianList.size();;
        ArrayList<Politician> tempList = finalPoliticianList;
        ArrayList<Question> questionList = Question.getQuestionsArr();
        int someInt = 0;
        Question tempQuestion = new Question();

        String brille = "brille";

        int brilleCount = 0;
        int neinCount = 0;

        for (int i = 0; i < limit; i++) {
            Politician tempPol = finalPoliticianList.get(i);

            brille = tempPol.brille;

            switch (brille) {
                case "brille":
                    brilleCount++;
                case "nein":
                    neinCount++;
                default:
                    someInt++;
            }
        }

        int durchschnitt = (brilleCount + neinCount) / 2;
        ArrayList<Integer> someList = new ArrayList<Integer>();
        ArrayList<String> anotherList = new ArrayList<String>();

        someList.add(brilleCount);
        anotherList.add("brille");
        someList.add(neinCount);
        anotherList.add("nein");

        int tempInt = 0;
        String tempString = "";

        for (int i = 0; i < 2; i++) {
            if (durchschnitt - someList.get(i) > tempInt && someList.get(i) != 0) {
                tempInt = someList.get(i);
                tempString = anotherList.get(i);
                probBrille = durchschnitt - someList.get(i);
            }
        }
        return tempString;
    }

    public static String getProbabilityAmt() {

        // Teil eines Versuchs einer "intelligenten" Fragenfindung

        int limit = finalPoliticianList.size();;
        ArrayList<Politician> tempList = finalPoliticianList;
        ArrayList<Question> questionList = Question.getQuestionsArr();
        int someInt = 0;
        Question tempQuestion = new Question();

        String amt = "amt";

        int kanzlerCount = 0;
        int praesidentCount = 0;
        int landeshauptCount = 0;
        int ministerCount = 0;
        int buergermeisterCount = 0;
        int diktatorCount = 0;
        int parteiobmannCount = 0;
        int vizekanzlerCount = 0;
        int nationalratCount = 0;
        int vizebuergCount = 0;
        int monarchCount = 0;
        int stadtratCount = 0;
        int gouvCount = 0;

        for (int i = 0; i < limit; i++) {
            Politician tempPol = finalPoliticianList.get(i);

            amt = tempPol.amt;

            switch (amt) {
                case "kanzler":
                    kanzlerCount++;
                case "praesident":
                    praesidentCount++;
                case "landeshaupt":
                    landeshauptCount++;
                case "minister":
                    ministerCount++;
                case "buergermeister":
                    buergermeisterCount++;
                case "diktator":
                    diktatorCount++;
                case "parteiobmann":
                    parteiobmannCount++;
                case "vizekanzler":
                    vizekanzlerCount++;
                case "nationalratsabgeordneter":
                    nationalratCount++;
                case "vizebuergermeister":
                    vizebuergCount++;
                case "monarch":
                    monarchCount++;
                case "stadtrat":
                    stadtratCount++;
                case "gouverneur":
                    gouvCount++;
                default:
                    someInt++;
            }
        }

        int durchschnitt = (kanzlerCount + praesidentCount + landeshauptCount + ministerCount + buergermeisterCount + diktatorCount + parteiobmannCount + vizekanzlerCount + nationalratCount + vizebuergCount + monarchCount + stadtratCount + gouvCount) / 13;
        ArrayList<Integer> someList = new ArrayList<Integer>();
        ArrayList<String> anotherList = new ArrayList<String>();

        someList.add(kanzlerCount);
        anotherList.add("kanzler");
        someList.add(praesidentCount);
        anotherList.add("praesident");
        someList.add(landeshauptCount);
        anotherList.add("landeshaupt");
        someList.add(ministerCount);
        anotherList.add("minister");
        someList.add(buergermeisterCount);
        anotherList.add("buergermeister");
        someList.add(diktatorCount);
        anotherList.add("diktator");
        someList.add(parteiobmannCount);
        anotherList.add("parteiobmann");
        someList.add(vizekanzlerCount);
        anotherList.add("vizekanzler");
        someList.add(nationalratCount);
        anotherList.add("nationalratsabgeordneter");
        someList.add(vizebuergCount);
        anotherList.add("vizebuergermeister");
        someList.add(monarchCount);
        anotherList.add("monarch");
        someList.add(stadtratCount);
        anotherList.add("stadtrat");
        someList.add(gouvCount);
        anotherList.add("gouverneur");

        int tempInt = 0;
        String tempString = "";

        for (int i = 0; i < 13; i++) {
            if (durchschnitt - someList.get(i) > tempInt && someList.get(i) != 0) {
                tempInt = someList.get(i);
                tempString = anotherList.get(i);
                probAmt = durchschnitt - someList.get(i);
            }
        }
        return tempString;
    }

    public static String getProbabilityPartei() {

        // Teil eines Versuchs einer "intelligenten" Fragenfindung

        int limit = finalPoliticianList.size();;
        ArrayList<Politician> tempList = finalPoliticianList;
        ArrayList<Question> questionList = Question.getQuestionsArr();
        int someInt = 0;
        Question tempQuestion = new Question();

        String partei = "partei";
        // BZOE, FPOE, SPOE, OEVP, GRUENEN, NEOS, NSDAP, LUGNER, STRONACH, BIERPARTEI, parteilos

        int bzoeCount = 0;
        int fpoeCount = 0;
        int spoeCount = 0;
        int oevpCount = 0;
        int gruenenCount = 0;
        int neosCount = 0;
        int nsdapCount = 0;
        int lugnerCount = 0;
        int stronachCount = 0;
        int bierCount = 0;
        int parteilosCount = 0;

        for (int i = 0; i < limit; i++) {
            Politician tempPol = finalPoliticianList.get(i);

            partei = tempPol.partei;

            switch (partei) {
                case "BZOE":
                    bzoeCount++;
                case "FPOE":
                    fpoeCount++;
                case "SPOE":
                    spoeCount++;
                case "OEVP":
                    oevpCount++;
                case "GRUENEN":
                    gruenenCount++;
                case "NEOS":
                    neosCount++;
                case "NSDAP":
                    nsdapCount++;
                case "LUGNER":
                    lugnerCount++;
                case "STRONACH":
                    stronachCount++;
                case "BIERPARTEI":
                    bierCount++;
                case "parteilos":
                    parteilosCount++;
                default:
                    someInt++;
            }
        }

        int durchschnitt = (bzoeCount + fpoeCount + spoeCount + oevpCount + gruenenCount + neosCount + nsdapCount + lugnerCount + stronachCount + bierCount + parteilosCount) / 11;
        ArrayList<Integer> someList = new ArrayList<Integer>();
        ArrayList<String> anotherList = new ArrayList<String>();

        someList.add(bzoeCount);
        anotherList.add("BZOE");
        someList.add(fpoeCount);
        anotherList.add("FPOE");
        someList.add(spoeCount);
        anotherList.add("SPOE");
        someList.add(oevpCount);
        anotherList.add("OEVP");
        someList.add(gruenenCount);
        anotherList.add("GRUENEN");
        someList.add(neosCount);
        anotherList.add("NEOS");
        someList.add(nsdapCount);
        anotherList.add("NSDAP");
        someList.add(lugnerCount);
        anotherList.add("LUGNER");
        someList.add(stronachCount);
        anotherList.add("STRONACH");
        someList.add(bierCount);
        anotherList.add("BIERPARTEI");
        someList.add(parteilosCount);
        anotherList.add("parteilos");

        int tempInt = 0;
        String tempString = "";

        for (int i = 0; i < 11; i++) {
            if (durchschnitt - someList.get(i) > tempInt && someList.get(i) != 0) {
                tempInt = someList.get(i);
                tempString = anotherList.get(i);
                probPartei = durchschnitt - someList.get(i);
            }
        }
        return tempString;
    }

    public static String getProbabilityHaare() {

        // Teil eines Versuchs einer "intelligenten" Fragenfindung

        int limit = finalPoliticianList.size();;
        ArrayList<Politician> tempList = finalPoliticianList;
        ArrayList<Question> questionList = Question.getQuestionsArr();
        int someInt = 0;
        Question tempQuestion = new Question();

        String haare = "haare";

        int glatzeCount = 0;
        int grauCount = 0;
        int braunCount = 0;
        int schwarzCount = 0;
        int blondCount = 0;

        for (int i = 0; i < limit; i++) {
            Politician tempPol = finalPoliticianList.get(i);

            haare = tempPol.haare;

            switch (haare) {
                case "Glatze":
                    glatzeCount++;
                case "grau":
                    grauCount++;
                case "braun":
                    braunCount++;
                case "schwarz":
                    schwarzCount++;
                case "blond":
                    blondCount++;
                default:
                    someInt++;
            }
        }

        int durchschnitt = (glatzeCount + grauCount + braunCount + schwarzCount + blondCount) / 5;
        ArrayList<Integer> someList = new ArrayList<Integer>();
        ArrayList<String> anotherList = new ArrayList<String>();

        someList.add(glatzeCount);
        anotherList.add("Glatze");
        someList.add(grauCount);
        anotherList.add("grau");
        someList.add(braunCount);
        anotherList.add("braun");
        someList.add(schwarzCount);
        anotherList.add("schwarz");
        someList.add(blondCount);
        anotherList.add("blond");

        int tempInt = 0;
        String tempString = "";

        for (int i = 0; i < 5; i++) {
            if (durchschnitt - someList.get(i) > tempInt && someList.get(i) != 0) {
                tempInt = someList.get(i);
                tempString = anotherList.get(i);
                probHaare = durchschnitt - someList.get(i);
            }
        }
        return tempString;
    }

    public static String getProbabilityGeschlecht() {

        // Teil eines Versuchs einer "intelligenten" Fragenfindung

        int limit = finalPoliticianList.size();;
        ArrayList<Politician> tempList = finalPoliticianList;
        ArrayList<Question> questionList = Question.getQuestionsArr();
        int someInt = 0;
        Question tempQuestion = new Question();

        String geschlecht = "geschlecht";
        // weiblich, maennlich

        int wCount = 0;
        int mCount = 0;

        for (int i = 0; i < limit; i++) {
            Politician tempPol = finalPoliticianList.get(i);

            geschlecht = tempPol.geschlecht;

            switch (geschlecht) {
                case "weiblich":
                    wCount++;
                case "maennlich":
                    mCount++;
                default:
                    someInt++;
            }
        }

        int durchschnitt = (wCount + mCount) / 2;
        ArrayList<Integer> someList = new ArrayList<Integer>();
        ArrayList<String> anotherList = new ArrayList<String>();

        someList.add(wCount);
        anotherList.add("weiblich");
        someList.add(mCount);
        anotherList.add("weiblich");

        int tempInt = 0;
        String tempString = "";

        for (int i = 0; i < 2; i++) {
            if (durchschnitt - someList.get(i) > tempInt && someList.get(i) != 0) {
                tempInt = someList.get(i);
                tempString = anotherList.get(i);
                probGeschlecht = durchschnitt - someList.get(i);
            }
        }
        return tempString;
    }

    public static String getProbabilityAktiv() {

        // Teil eines Versuchs einer "intelligenten" Fragenfindung

        int limit = finalPoliticianList.size();;
        ArrayList<Politician> tempList = finalPoliticianList;
        ArrayList<Question> questionList = Question.getQuestionsArr();
        int someInt = 0;
        Question tempQuestion = new Question();

        String aktiv = "aktiv";
        // aktiv, nein

        int aktivCount = 0;
        int neinCount = 0;

        for (int i = 0; i < limit; i++) {
            Politician tempPol = finalPoliticianList.get(i);

            aktiv = tempPol.aktiv;

            switch (aktiv) {
                case "aktiv":
                    aktivCount++;
                case "nein":
                    neinCount++;
                default:
                    someInt++;
            }
        }

        int durchschnitt = (neinCount + aktivCount) / 2;
        ArrayList<Integer> someList = new ArrayList<Integer>();
        ArrayList<String> anotherList = new ArrayList<String>();

        someList.add(aktivCount);
        anotherList.add("aktiv");
        someList.add(neinCount);
        anotherList.add("nein");

        int tempInt = 0;
        String tempString = "";

        for (int i = 0; i < 2; i++) {
            if (durchschnitt - someList.get(i) > tempInt && someList.get(i) != 0) {
                tempInt = someList.get(i);
                tempString = anotherList.get(i);
                probAktiv = durchschnitt - someList.get(i);
            }
        }
        return tempString;
    }

    public static void createPoliticians() {

        // Diese Methode wird zu Beginn des Programms aufgerufen.
        // Es erstellt aus den Einträgen des JSON Files eine Arraylist aus den Objekten (Politikern) (name, geschlecht, ...)

        fillList();

        // Da die Liste der Antworten sofort für die Fragenfindung verwendet wird, muss einmal die Liste befüllt werden.

        String alter;
        String name;
        String geschlecht;
        String aktiv;
        String haare;
        String partei;
        String amt;
        String brille;

        ArrayList<Politician> politiciansList = new ArrayList();

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("jsonData.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray politiciansArray = (JSONArray) jsonObject.get("politicians");
            for (Object objInArr : politiciansArray) {
                JSONObject jsonPolitician = (JSONObject) objInArr;
                name = (String) jsonPolitician.get("name");
                geschlecht = (String) jsonPolitician.get("geschlecht");
                alter = (String) jsonPolitician.get("alter");
                aktiv = (String) jsonPolitician.get("aktiv");
                haare = (String) jsonPolitician.get("haare");
                partei = (String) jsonPolitician.get("partei");
                amt = (String) jsonPolitician.get("amt");
                brille = (String) jsonPolitician.get("brille");

                Politician politician = new Politician(name, geschlecht, alter, aktiv, haare, partei, amt, brille);
                politiciansList.add(politician);

                }
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int i = 0;

        finalPoliticianList = politiciansList;

    }

    public static ArrayList<Politician> getPoliticiansArr() {

        return finalPoliticianList;
    }

}
