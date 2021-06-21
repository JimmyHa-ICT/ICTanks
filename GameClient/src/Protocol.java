public class Protocol {
    private String message = "";

    public Protocol() {
    }

    public String RegisterPacket(int x, int y) {
        this.message = "Hello" + x + "," + y;
        return this.message;
    }

    public String UpdatePacket(int x, int y, int id, int dir) {
        this.message = "Update" + x + "," + y + "-" + dir + "|" + id;
        return this.message;
    }

    public String ShotPacket(int id) {
        this.message = "Shot" + id;
        return this.message;
    }

    public String RemoveClientPacket(int id) {
        this.message = "Remove" + id;
        return this.message;
    }

    public String ExitMessagePacket(int id) {
        this.message = "Exit" + id;
        return this.message;
    }

    public String CreateRoomPacket() {
        this.message = "register";
        return this.message;
    }
}

