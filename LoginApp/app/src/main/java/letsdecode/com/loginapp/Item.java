package letsdecode.com.loginapp;



public class Item {

    private String itemName;
    private String quantity;
    private String status;
    private final int id;

    public Item(String itemName, String quantity, String status, int id){
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
        this.id = id;


    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", quantity='" + quantity + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                '}';
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }






}
