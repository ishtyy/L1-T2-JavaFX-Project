package server;

import data.network.*;
import util.NetworkUtil;

import java.io.IOException;

public class ThreadServer implements Runnable {
    private NetworkUtil networkUtil;
    private Thread thread;
    private Server server;

    public ThreadServer(NetworkUtil networkUtil, Server server) {
        this.networkUtil = networkUtil;
        this.server = server;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = networkUtil.read();
                if (obj instanceof Message) {
                    Message msg = (Message) obj;
                    if (msg.getMessageHeader() == MessageHeader.CLUB_INFO) {
                        String clubName = msg.getMessage();
                        networkUtil.write(server.db.searchClub("ANY"));
                    } else if (msg.getMessageHeader() == MessageHeader.TRANSFER_WINDOW) {
//                        System.out.println("Written from server:");
//                        List<Player> list = server.getTransferPlayerList();
////                        System.out.println(list);
//                        Object ob = list;
//                        System.out.println(ob);
                        networkUtil.write(server.getTransferPlayerList());
                    } else if (msg.getMessageHeader() == MessageHeader.LOGOUT) {
                        networkUtil.write(server.logoutClub(msg.getMessage()));
                    } else if (msg.getMessageHeader() == MessageHeader.CLUB_LIST) {
                        networkUtil.write(server.sendClubList());
                    }else if(msg.getMessageHeader() == MessageHeader.NOTIFICATION){
                        networkUtil.write(server.getNotification(msg.getMessage()));
                    }
                } else if (obj instanceof LoginInfo) {
                    LoginInfo loginInfo = (LoginInfo) obj;
                    if (loginInfo.getMessageHeader() == MessageHeader.REGISTER) {
                        networkUtil.write(server.registerClub(
                                loginInfo.getUsername(), loginInfo.getPassword(), networkUtil));
                    } else if (loginInfo.getMessageHeader() == MessageHeader.LOGIN) {
                        networkUtil.write(server.loginClub(loginInfo.getUsername(), loginInfo.getPassword()));
                    } else if (loginInfo.getMessageHeader() == MessageHeader.CHANGE_PASS) {
                        networkUtil.write(server.changePassword(loginInfo.getUsername(), loginInfo.getPassword(),
                                loginInfo.getNewPassword()));
                    }
                } else if (obj instanceof BuyInfo) {
                    BuyInfo buyInfo = (BuyInfo) obj;
                    if (buyInfo.getMessageHeader() == MessageHeader.BUY) {
//                        System.out.println("buy info object received");
                        server.addNotification(buyInfo.getClubName(),buyInfo.getPlayerName() + " has been bought by " + buyInfo.getClubName());
                        String clubName = server.db.searchPlayerByName(buyInfo.getPlayerName()).getClub();
                        server.addNotification(clubName,buyInfo.getPlayerName() + " has been sold to " + buyInfo.getClubName());
                        server.addNotification("ANY",buyInfo.getPlayerName() + " has been sold to " + buyInfo.getClubName() + " from " + clubName);
                        System.out.println(buyInfo.getClubName() + " has bought " + buyInfo.getPlayerName());

                        networkUtil.write(server.sellPlayer(buyInfo.getPlayerName(), buyInfo.getClubName()));
                       }
                } else if (obj instanceof SaleInfo) {
                    SaleInfo saleInfo = (SaleInfo) obj;
                    if (saleInfo.getMessageHeader() == MessageHeader.SELL) {
                        networkUtil.write(server.addToTransferWindow(saleInfo.getPlayerName(), saleInfo.getPlayerPrice()));
                    }
                }else if(obj instanceof  Cancel){
                    Cancel cancel = (Cancel) obj;
                    if(cancel.getMessageHeader() == MessageHeader.CANCEL){
                        networkUtil.write(server.cancelTransfer(cancel.getPlayerName()));
                    }
                }
            }
        }
        catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
//                System.out.println("Disconnected successfully");
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }
}

