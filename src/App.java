import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {
	public static void main(String[] args) {
		Path awardFile = Paths
				.get("src/awards.csv");
		Path contractFile = Paths
				.get("src/contracts.csv");

		List<Award> awardsList = new ArrayList<Award>();
		List<Contract> contractList = new ArrayList<Contract>();
		List<Final> finalList = new ArrayList<Final>();
		try {
			FileWriter fw = new FileWriter("src/final.csv");
			PrintWriter pw = new PrintWriter(fw);
			List<String> awardLines = Files.readAllLines(awardFile,
					StandardCharsets.UTF_8);
			List<String> contractLines = Files.readAllLines(contractFile,
					StandardCharsets.UTF_8);

			for (int i = 0; i < awardLines.size(); i++) {
				if (i != 0) {
					String[] splittedAwardLine = awardLines.get(i).split(",");
					Award awardObj = new Award();
					awardObj.setContractName(splittedAwardLine[0]);
					awardObj.setContractDate(splittedAwardLine[1]);
					awardObj.setCompletionDate(splittedAwardLine[2]);
					awardObj.setAwardee(splittedAwardLine[3]);
					awardObj.setAwardeeLocation(splittedAwardLine[4]);
					awardObj.setAmount(Integer.parseInt(splittedAwardLine[5]));
					awardsList.add(awardObj);
				}
			}

			for (int i = 0; i < contractLines.size(); i++) {
				if (i != 0) {
					String[] splittedContractLine = contractLines.get(i).split(",");
					Contract contractObj = new Contract();
					contractObj.setContractName(splittedContractLine[0]);
					contractObj.setStatus(splittedContractLine[1]);
					contractObj.setBidPurchaseDeadline(splittedContractLine[2]);
					contractObj.setBidSubmissionDeadline(splittedContractLine[3]);
					contractObj.setBidOpeningDate(splittedContractLine[4]);
					contractObj.setTenderid(splittedContractLine[5]);
					contractObj.setPublicationDate(splittedContractLine[6]);
					contractObj.setPublishedIn(splittedContractLine[7]);
					contractList.add(contractObj);

					Final finalObj = new Final();
					finalObj.setContractName(splittedContractLine[0]);
					finalObj.setStatus(splittedContractLine[1]);
					finalObj.setBidPurchaseDeadline(splittedContractLine[2]);
					finalObj.setBidSubmissionDeadline(splittedContractLine[3]);
					finalObj.setBidOpeningDate(splittedContractLine[4]);
					finalObj.setTenderid(splittedContractLine[5]);
					finalObj.setPublicationDate(splittedContractLine[6]);
					finalObj.setPublishedIn(splittedContractLine[7]);
					finalList.add(finalObj);
				}
			}

			for (Final f : finalList) {
				for (Award a : awardsList) {
					if (f.getContractName().equals(a.getContractName())) {
						f.setContractName(a.getContractName());
						f.setContractDate(a.getContractDate());
						f.setCompletionDate(a.getCompletionDate());
						f.setAwardee(a.getAwardee());
						f.setAwardeeLocation(a.getAwardeeLocation());
						f.setAmount(a.getAmount());
					}
				}
			}
			pw.println("contractname,status,bidPurchaseDeadline,bidSubmissionDeadline,bidOpeningDate,tenderid,publicationDate,publishedIn,contractDate,completionDate,awardee,awardeeLocation,Amount ");
			for (Final f : finalList) {
				pw.print(f.getContractName()+",");
				pw.print(f.getStatus()+",");
				pw.print(f.bidPurchaseDeadline+",");
				pw.print(f.bidSubmissionDeadline+",");
				pw.print(f.bidOpeningDate+",");
				pw.print(f.tenderid+",");
				pw.print(f.publicationDate+",");
				pw.print(f.publishedIn+",");
				pw.print(f.contractDate+",");
				pw.print(f.completionDate+",");
				pw.print(f.awardee+",");
				pw.print(f.awardeeLocation+",");
				pw.print(f.amount+"\n");
			}
			pw.flush();
			pw.close();
			fw.close();       
			
			Integer amountTotal = 0;

			for (Final f : finalList) {
				if (f.getStatus().equals("Closed")) {
					int x = f.getAmount();
					amountTotal = amountTotal + x;
				}
			}
			System.out.println("Total Amount of closed contracts:" + amountTotal);


		} catch (IOException e) {
			System.out.println("Files could not be read");
			e.printStackTrace();
		}

	}
}
