package bis;

import dao.*;
import model.*;

public class BISDashBoard {
	public String GetTotalUsageOfCustomer(Request request) {
		return new DAODashBoard().GetTotalUsageOfCustomer(request);
	}

	public String GetTotalNumberOfArticle(Request request) {
		return new DAODashBoard().GetTotalNumberOfArticle(request);
	}

}
