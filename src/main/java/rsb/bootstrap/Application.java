package rsb.bootstrap;

import rsb.bootstrap.hardcoded.DevelopmentOnlyCustomerService;

public class Application {

    public static void main(String[] args) {
        var customerService = new DevelopmentOnlyCustomerService();
        Demo.workWithCustomerService(Application.class, customerService);
    }
}
