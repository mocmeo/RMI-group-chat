
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Scanner;

public class ChatServer {

	public static void main(String[] args) {
		try {
			// enter username
			Scanner in = new Scanner(System.in);
			System.out.print("Enter your name: ");
			String name = in.nextLine().trim();

			// register a name to RMI registry, for binding server
			LocateRegistry.createRegistry(1099);
			ChatImp server = new ChatImp(name);
			String url = "rmi://localhost/ABC";
			Naming.rebind(url, server);

			System.out.println("[System] Server is ready...");
			
			while (true) {
				String msg = in.nextLine().trim();

				// broadcasting message to all clients
				if (server.getClients() != null) {
					List<IChat> clients = server.getClients();
					msg = "[" + server.getName() + "] " + msg;
					for (IChat iClient : clients) {
						iClient.send(msg);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("[System] Server failed: " + e);
		}

	}
}