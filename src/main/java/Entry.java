public class Entry {
    public String id;
    public String description;
    public String closingDate;
    public String closingQuantity;
    public String openingDate;
    public String buyingCost;
    public String sellingFee;
    public String sellingCost;
    public String withholdingTax;
    public String country;
    public String currency;

    public Entry(String[] row) {
        this.id = row[0];
        this.description = row[1];
        this.closingQuantity = row[2];
        this.openingDate = row[3];
        this.buyingCost = row[4];
        this.sellingFee = row[5];
        this.sellingCost = row[6];
        this.withholdingTax = row[7];
        this.country = row[8];
        this.currency = row[9];
        this.closingDate = row[10];
    }
}