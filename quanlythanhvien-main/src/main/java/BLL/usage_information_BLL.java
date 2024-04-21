package BLL;

import BLL.DTO.usage_information;
import DAL.usage_information_DAL;

import java.util.ArrayList;

public class usage_information_BLL {

    static ArrayList<usage_information> usage_information_list;
    private final usage_information_DAL data = new usage_information_DAL();

    public usage_information_BLL() {}

    public static ArrayList<usage_information> get_usage_information_list() {
        return usage_information_list;
    }

    public static void setUsage_information_list(ArrayList<usage_information> usage_information_list) {
        usage_information_BLL.usage_information_list = usage_information_list;
    }

    //load database into table
    public ArrayList<usage_information> load_usage_information_list() {
        return (ArrayList<usage_information>) data.load_usage_information();
    }
}
