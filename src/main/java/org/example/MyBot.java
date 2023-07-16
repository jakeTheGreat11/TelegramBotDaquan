package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {
    private String StageApiSetter = "";
    private BotWindow botWindow = new BotWindow();
    private String jokeAPIMessage = "Couldn't fetch a joke: ";

    private String quotesApiQuote = "couldn't fetch quote ";
    private String quotesApiAuthor = " couldn't fetch author";


    private String numberFactApiFact = "couldn't fetch number fact";


    private int countryApiPopulation;
    private String countryApiName = "Couldn't fetch a name: ";
    private List<String> countryApiBorders;
    private String countryApiCapital = "Couldn't fetch a capital: ";


    private String covid19DataDate = "Couldn't fetch a name: ";
    private int covid19DataPositive = 0;
    private int covid19DataNegative = 0;
    private int covid19DataDeath = 0;


    private Set<Long> chatIds = new HashSet<>();
    private Map<Long, Integer> levelsMap = new HashMap<>();


    private Queue<String[]> interactionsHistory = new LinkedList<>();
    // [name, interaction, time], []

    private String[] interaction = new String[3];
    // [name, interaction, time]

    private int uniqueUserCounter = 0;

    private int JokesApiCounter = 0;
    private int Covid19DatApiCounter = 0;
    private int QuotesApiCounter = 0;
    private int CountryApiCounter = 0;
    private int NumberFactApiCounter = 0;


    private HashMap<String, Integer> MostActiveUserMap = new HashMap<>();


    public void sendMessage(String message) {
        chatIds.forEach(id -> {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(id);
            sendMessage.setText(message);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public String getBotToken() {
        return "6260425053:AAFyBTHi615hCwiG_fsKiqw5V618-EGJn_U";
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId;
        String text = "";
        String username = "";
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            username = update.getCallbackQuery().getFrom().getFirstName();
        } else {
            chatId = update.getMessage().getChatId();
            username = update.getMessage().getFrom().getFirstName();
            text = update.getMessage().getText();
        }
        SendMessage sendMessage = new SendMessage();
        Integer level = levelsMap.get(chatId);
        sendMessage.setChatId(chatId);
        if (!this.chatIds.contains(chatId)) {
            uniqueUserCounter++;
            botWindow.getUserStatisticsPanel().setUniqueUsersText(String.valueOf(uniqueUserCounter));
        }
        this.chatIds.add(chatId);

        this.interaction[0] = "";
        this.interaction[1] = "";
        this.interaction[2] = "";

        if (text.equals("/start")) {
            System.out.println("in start");
            sendMessage.setText("Hi my name is Daquan, pick a api:");
            //interactions.add(["t", "start"],"get name of user")
            InlineKeyboardButton apiQuotesButton = new InlineKeyboardButton();
            apiQuotesButton.setText("Quotes");
            apiQuotesButton.setCallbackData("Quotes");

            InlineKeyboardButton apiJokesButton = new InlineKeyboardButton();
            apiJokesButton.setText("Jokes");
            apiJokesButton.setCallbackData("Jokes");

            InlineKeyboardButton apiCountryButton = new InlineKeyboardButton();
            apiCountryButton.setText("Country");
            apiCountryButton.setCallbackData("Country");

            InlineKeyboardButton apiDataCovid19Button = new InlineKeyboardButton();
            apiDataCovid19Button.setText("Data covid-19");
            apiDataCovid19Button.setCallbackData("Data covid-19");

            InlineKeyboardButton apiNumberFactsButton = new InlineKeyboardButton();
            apiNumberFactsButton.setText("Number facts");
            apiNumberFactsButton.setCallbackData("Number facts");

            List<InlineKeyboardButton> topRow = new ArrayList<>(List.of());
            if (botWindow.getApiOptions().getSelectedCheckboxes().get("Number facts") == 1) {
                topRow.add(apiNumberFactsButton);
            }
            if (botWindow.getApiOptions().getSelectedCheckboxes().get("Jokes") == 1) {
                topRow.add(apiJokesButton);
            }
            if (botWindow.getApiOptions().getSelectedCheckboxes().get("Quotes") == 1) {
                topRow.add(apiQuotesButton);
            }
            if (botWindow.getApiOptions().getSelectedCheckboxes().get("Data covid-19") == 1) {
                topRow.add(apiDataCovid19Button);
            }
            if (botWindow.getApiOptions().getSelectedCheckboxes().get("Country") == 1) {
                topRow.add(apiCountryButton);
            }
            List<List<InlineKeyboardButton>> keyboard = List.of(topRow);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            this.levelsMap.put(chatId, 1);
        } else {
            if (level == 1) {
                if (update.getCallbackQuery().getData().equals("Country")) {
                    sendMessage.setText("type in alpha3code:");
                    this.StageApiSetter = "country";
                    CountryApiCounter++;
                    this.levelsMap.put(chatId, 2);
                }
                if (update.getCallbackQuery().getData().equals("Jokes")) {
                    System.out.println("proper callback");
                    JokesApiCounter++;
                    jokesApiRun();
                    sleep();
                    sendMessage.setText(jokeAPIMessage);
                    this.levelsMap.put(chatId, 4);
                }
                if (update.getCallbackQuery().getData().equals("Quotes")) {
                    System.out.println("proper callback");
                    QuotesApiCounter++;
                    quotesApiRun();
                    sleep();
                    sendMessage.setText(quotesApiQuote + "-" + quotesApiAuthor);
                    this.levelsMap.put(chatId, 4);
                }
                if (update.getCallbackQuery().getData().equals("Data covid-19")) {
                    System.out.println("proper callback");
                    Covid19DatApiCounter++;
                    sendMessage.setText("enter desired date in yyyymmdd format:");
                    this.levelsMap.put(chatId, 2);
                    this.StageApiSetter = "covid";
                }
                if (update.getCallbackQuery().getData().equals("Number facts")) {
                    System.out.println("proper callback");
                    NumberFactApiCounter++;
                    sendMessage.setText("enter a number:");
                    this.StageApiSetter = "numbers";
                    this.levelsMap.put(chatId, 2);
                }

                if (!MostActiveUserMap.containsKey(username)) {
                    MostActiveUserMap.put(username, 0);
                } else {
                    MostActiveUserMap.put(username, MostActiveUserMap.get(username) + 1);
                }
                updateMostActiveUser();
                this.interaction[0] = "user:" + update.getCallbackQuery().getFrom().getFirstName() + " , ";
                this.interaction[1] = "api used: " + update.getCallbackQuery().getData() + " , ";
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = myDateObj.format(myFormatObj);
                this.interaction[2] = "Date/time: " + formattedDate;
                addInteraction(this.interaction);
                setHistoryLabels();
            } else if (level == 2) {
                if (this.StageApiSetter.equals("numbers")) {
                    numbersApiRun(text);
                    sleep();
                    sendMessage.setText(this.numberFactApiFact);
                }
                if (this.StageApiSetter.equals("country")) {
                    System.out.println("country code received");
                    countryApiRun(text);
                    sleep();
                    if (countryApiName == null) {
                        sendMessage.setText("sorry that is not a valid alpha3code\n enter a valid alpha3code:");
                        this.levelsMap.put(chatId, 2);
                    } else {
                        sendMessage.setText("what do you want to know about " + this.countryApiName + ":");
                        InlineKeyboardButton CountryOption1 = new InlineKeyboardButton();
                        CountryOption1.setText("capital");
                        CountryOption1.setCallbackData("capital");

                        InlineKeyboardButton CountryOption2 = new InlineKeyboardButton();
                        CountryOption2.setText("population");
                        CountryOption2.setCallbackData("population");

                        InlineKeyboardButton CountryOption3 = new InlineKeyboardButton();
                        CountryOption3.setText("borders");
                        CountryOption3.setCallbackData("borders");

                        List<InlineKeyboardButton> topRow = List.of(CountryOption1, CountryOption2, CountryOption3);
                        List<List<InlineKeyboardButton>> keyboard = List.of(topRow);
                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                        inlineKeyboardMarkup.setKeyboard(keyboard);
                        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                        this.levelsMap.put(chatId, 3);
                    }
                }
                if (StageApiSetter.equals("covid")) {
                    System.out.println("covid code received");
                    covidDataApiRun(text);
                    sleep();
                    sendMessage.setText("what do you want to know about on this day:");
                    InlineKeyboardButton CovidOption1 = new InlineKeyboardButton();
                    CovidOption1.setText("negative");
                    CovidOption1.setCallbackData("negative");

                    InlineKeyboardButton CovidOption2 = new InlineKeyboardButton();
                    CovidOption2.setText("positive");
                    CovidOption2.setCallbackData("positive");

                    InlineKeyboardButton CovidOption3 = new InlineKeyboardButton();
                    CovidOption3.setText("deaths");
                    CovidOption3.setCallbackData("deaths");

                    List<InlineKeyboardButton> topRow = List.of(CovidOption1, CovidOption2, CovidOption3);
                    List<List<InlineKeyboardButton>> keyboard = List.of(topRow);
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    inlineKeyboardMarkup.setKeyboard(keyboard);
                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                    this.levelsMap.put(chatId, 3);
                }
            } else if (level == 3) {
                if (update.getCallbackQuery().getData().equals("population")) {
                    System.out.println("proper callback");
                    sendMessage.setText(String.valueOf(this.countryApiPopulation));
                    this.levelsMap.put(chatId, 4);
                }
                if (update.getCallbackQuery().getData().equals("borders")) {
                    System.out.println("proper callback");
                    if (this.countryApiBorders.isEmpty()) {
                        sendMessage.setText(countryApiName + "has no borders");
                    } else {
                        String borderText = String.join(", ", this.countryApiBorders);
                        sendMessage.setText(borderText);
                    }
                    this.levelsMap.put(chatId, 4);
                }
                if (update.getCallbackQuery().getData().equals("capital")) {
                    sendMessage.setText(countryApiCapital);
                }
                if (update.getCallbackQuery().getData().equals("negative")) {
                    sendMessage.setText(String.valueOf(this.covid19DataNegative));
                    this.levelsMap.put(chatId, 4);
                }
                if (update.getCallbackQuery().getData().equals("positive")) {
                    System.out.println("proper callback");
                    sendMessage.setText(String.valueOf(this.covid19DataPositive));
                    this.levelsMap.put(chatId, 4);
                }
                if (update.getCallbackQuery().getData().equals("deaths")) {
                    System.out.println("proper callback");
                    sendMessage.setText(String.valueOf(this.covid19DataDeath));
                    this.levelsMap.put(chatId, 4);
                }
            }
        }
        botWindow.getUserStatisticsPanel().setMostPopularApiText(getFirsKeyValue());
        botWindow.getUserStatisticsPanel().setApiRequestCountText(String.valueOf(JokesApiCounter + Covid19DatApiCounter + QuotesApiCounter + CountryApiCounter + NumberFactApiCounter));
        updateChartImage();
        try {
            System.out.println("in execute message");
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getBotUsername() {
        return "daquanApi_bot";
    }


    public synchronized void countryApiRun(String name) {
        new Thread(() -> {
            try {
                HttpResponse<String> response = Unirest.get("https://restcountries.com/v2/alpha/" + name).asString();
                ObjectMapper objectMapper = new ObjectMapper();
                CountryModel countryModel = objectMapper.readValue(response.getBody(), CountryModel.class);
                System.out.println(response.getBody());
                this.countryApiName = countryModel.getName();
                this.countryApiBorders = countryModel.getBorders();
                this.countryApiCapital = countryModel.getCapital();
                this.countryApiPopulation = countryModel.getPopulation();
            } catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }

        }).start();
    }


    public synchronized void covidDataApiRun(String date) {
        new Thread((() -> {
            try {
                HttpResponse<String> response = Unirest.get("https://api.covidtracking.com/v1/us/" + date + ".json").asString();
                ObjectMapper objectMapper = new ObjectMapper();
                Covid19DataModel covid19DataModel = objectMapper.readValue(response.getBody(), Covid19DataModel.class);
                System.out.println(response.getBody());
                this.covid19DataNegative = covid19DataModel.getNegative();
                this.covid19DataPositive = covid19DataModel.getPositive();
                this.covid19DataDeath = covid19DataModel.getDeath();

            } catch (Exception e) {
                System.out.println("error");
            }
        })).start();
    }

    public void jokesApiRun() {
        new Thread((() -> {
            try {
                HttpResponse<String> response = Unirest.get("https://v2.jokeapi.dev/joke/Any?type=single").asString();
                ObjectMapper objectMapper = new ObjectMapper();
                JokeModel jokeModel = objectMapper.readValue(response.getBody(), JokeModel.class);
                this.jokeAPIMessage = jokeModel.getJoke();
            } catch (Exception e) {
                System.out.println("error");
            }
        })).start();
    }

    public void quotesApiRun() {
        new Thread((() -> {
            try {
                HttpResponse<String> response = Unirest.get("https://dummyjson.com/quotes/random").asString();
                ObjectMapper objectMapper = new ObjectMapper();
                QuotesModel quotesModel = objectMapper.readValue(response.getBody(), QuotesModel.class);
                System.out.println(response.getBody());
                this.quotesApiQuote = quotesModel.getQuote();
                this.quotesApiAuthor = quotesModel.getAuthor();
            } catch (Exception e) {
                System.out.println("error");
            }

        })).start();
    }

    public synchronized void numbersApiRun(String number) {
        new Thread((() -> {
            try {
                HttpResponse<String> response = Unirest.get("http://numbersapi.com/" + number).asString();
                this.numberFactApiFact = response.getBody();
            } catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }
        })).start();
    }

    public void addInteraction(String[] interaction) {
        if (this.interactionsHistory.size() == 10) {
            this.interactionsHistory.poll();
        }
        this.interactionsHistory.add(interaction);

        //check if there are ten interactions already
        //if there is then take out the last one
        //if theres less then 10 just add the interaction
    }

    public void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHistoryLabels() {
        System.out.println("made it");
        Queue<String[]> copyHistory = this.interactionsHistory;
        int size = copyHistory.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                botWindow.getInteractionHistoryPanel().updateHistory(copyHistory.peek());
            }
            copyHistory.add(copyHistory.peek());
            copyHistory.poll();
        }
    }

    public String getFirsKeyValue() {
        int max = Math.max(JokesApiCounter, CountryApiCounter);
        max = Math.max(max, Covid19DatApiCounter);
        max = Math.max(max, QuotesApiCounter);
        max = Math.max(max, NumberFactApiCounter);
        if (max == JokesApiCounter) {
            return "Jokes";
        } else if (max == CountryApiCounter) {
            return "Country";
        } else if (max == Covid19DatApiCounter) {
            return "Covid-19 data";
        } else if (max == QuotesApiCounter) {
            return "Quotes";
        } else if (max == NumberFactApiCounter) {
            return "Number facts";
        }
        return "";
    }

    public void updateMostActiveUser() {
        int max = 0;
        String mostActive = "";
        for (String username : MostActiveUserMap.keySet()) {
            if (MostActiveUserMap.get(username) >= max) {
                max = MostActiveUserMap.get(username);
                mostActive = username;
            }
        }
        botWindow.getUserStatisticsPanel().setMostActiveUserText("the most active user: " + mostActive);
    }

    private void updateChartImage() {
        new Thread((()->{
        try {
            QuickChart chart = new QuickChart();
            chart.setWidth(500);
            chart.setHeight(300);
            chart.setConfig("{"
                    + "    type: 'bar',"
                    + "    data: {"
                    + "        labels: ['Number fact', 'Quotes', 'Covid-19 data', 'Country', 'Jokes'],"
                    + "        datasets: [{"
                    + "            label: 'Api requests',"
                    + "            data: [" + NumberFactApiCounter + ", " + QuotesApiCounter + ", " + Covid19DatApiCounter + ", " + CountryApiCounter + ", " + JokesApiCounter + "]"
                    + "        }]"
                    + "    }"
                    + "}");

            URL chartUrl = new URL(chart.getUrl());
            BufferedImage chartImage = ImageIO.read(chartUrl);
            botWindow.getChartPanel().getChartLabel().setIcon(new ImageIcon(chartImage));
            botWindow.getChartPanel().revalidate();
            botWindow.getChartPanel().repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
        })).start();
    }

}
