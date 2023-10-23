package fullmeshtopology;
import java.util.*;

class WorkStation {
    String name;
    String ipAddress;
    HashSet<WorkStation> connections;

    WorkStation(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
        connections = new HashSet<>();
    }
}

class Network {
    ArrayList<WorkStation> workstations;

    Network() {
        workstations = new ArrayList<>();
    }

    void addNode(String name, String ipAddress) {
        WorkStation newWorkStation = new WorkStation(name, ipAddress);
        for (WorkStation ws : workstations) {
            ws.connections.add(newWorkStation);
            newWorkStation.connections.add(ws);
        }
        workstations.add(newWorkStation);
    }

    WorkStation getNodeByIP(String ip) {
        for (WorkStation ws : workstations) {
            if (ws.ipAddress.equals(ip)) {
                return ws;
            }
        }
        return null;
    }
}

class BFSReader {
    void bfsRead(WorkStation ws) {
        LinkedList<WorkStation> queue = new LinkedList<>();
        HashSet<WorkStation> visited = new HashSet<>();
        queue.add(ws);

        while (!queue.isEmpty()) {
            WorkStation current = queue.poll();
            System.out.println("Connected to: " + current.name + ", IP: " + current.ipAddress);
            visited.add(current);

            for (WorkStation w : current.connections) {
                if (!visited.contains(w)) {
                    queue.add(w);
                    visited.add(w);
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Network nw = new Network();
        BFSReader bfsReader = new BFSReader();
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("1.Add node\n2.Connection Test\n3.Exit");
            int choice = sc.nextInt();
            switch(choice) {
                case 1:
                    System.out.println("Enter name:");
                    String name = sc.next();
                    System.out.println("Enter IP Address:");
                    String ip = sc.next();
                    nw.addNode(name, ip);
                    break;

                case 2:
                    System.out.println("Enter IP Address to test connection:");
                    ip = sc.next();
                    WorkStation ws = nw.getNodeByIP(ip);
                    if(ws == null) {
                        System.out.println("Invalid IP Address, Node not found");
                        break;
                    }
                    bfsReader.bfsRead(ws);
                    break;

                case 3:
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
}
