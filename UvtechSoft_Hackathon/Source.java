import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Source {
    
    private static final double OFFICE_LAT = 16.86114504153856;
    private static final double OFFICE_LON = 74.57615831791716;
    private static final int BIKE_SPEED = 35; 
    private static final int CYCLE_SPEED = 15; 
    private static final int HANDLING_TIME = 15; 
    
    private static final int HOME_START = 420; 
    private static final int HOME_END = 1260;
    private static final int OFFICE_START = 600;
    private static final int OFFICE_END = 1080; 
    private static final int MORNING_SHIFT_START = 420;
    private static final int MORNING_SHIFT_END = 840;
    private static final int EVENING_SHIFT_START = 840;
    private static final int EVENING_SHIFT_END = 1260;

    static class DeliveryBoy {
        private String deliveryBoyId;
        private String shift;
        private String vehicleType;
        
        public DeliveryBoy(String deliveryBoyId, String shift, String vehicleType) {
            this.deliveryBoyId = deliveryBoyId;
            this.shift = shift;
            this.vehicleType = vehicleType;
        }
        
        public String getDeliveryBoyId() { return deliveryBoyId; }
        public String getShift() { return shift; }
        public String getVehicleType() { return vehicleType; }
        
        @Override
        public String toString() {
            return "DeliveryBoy{id=" + deliveryBoyId + ", shift=" + shift + ", vehicle=" + vehicleType + "}";
        }
    }

    static class Order {
        private String orderId;
        private double latitude;
        private double longitude;
        private String deliveryType;
        
        public Order(String orderId, double latitude, double longitude, String deliveryType) {
            this.orderId = orderId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.deliveryType = deliveryType;
        }
        
        public String getOrderId() { return orderId; }
        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
        public String getDeliveryType() { return deliveryType; }
        
        @Override
        public String toString() {
            return "Order{id=" + orderId + ", lat=" + latitude + ", lon=" + longitude + ", type=" + deliveryType + "}";
        }
    }

    static class Assignment {
        String orderId;
        String deliveryBoyId;
        int startTime;
        int endTime;
        double distance;
        
        Assignment(String orderId, String deliveryBoyId, int startTime, int endTime, double distance) {
            this.orderId = orderId;
            this.deliveryBoyId = deliveryBoyId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.distance = distance;
        }
    }

    private static List<DeliveryBoy> deliveryBoys = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static double[][] distanceMatrix;
    private static List<Assignment> assignments = new ArrayList<>();

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth's radius in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private static List<Order> getOrdersByType(String type) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getDeliveryType().equals(type)) {
                result.add(order);
            }
        }
        return result;
    }
    
    private static List<DeliveryBoy> getDeliveryBoysByShift(String shift) {
        List<DeliveryBoy> result = new ArrayList<>();
        for (DeliveryBoy db : deliveryBoys) {
            if (db.getShift().equals(shift)) {
                result.add(db);
            }
        }
        return result;
    }
    
    private static List<DeliveryBoy> getDeliveryBoysByVehicle(String vehicleType) {
        List<DeliveryBoy> result = new ArrayList<>();
        for (DeliveryBoy db : deliveryBoys) {
            if (db.getVehicleType().equals(vehicleType)) {
                result.add(db);
            }
        }
        return result;
    }

    private static void parseDeliveryBoys(String json) {
        String[] items = json.split("\\{");
        for (String item : items) {
            if (item.contains("DELIVERY_BOY_ID")) {
                String id = extractValue(item, "DELIVERY_BOY_ID");
                String shift = extractValue(item, "SHIFT");
                String vehicleType = extractValue(item, "VEHICLE_TYPE");
                if (id != null && shift != null && vehicleType != null) {
                    deliveryBoys.add(new DeliveryBoy(id, shift, vehicleType));
                }
            }
        }
    }
    
    private static void parseOrders(String json) {
        String[] items = json.split("\\{");
        for (String item : items) {
            if (item.contains("ORDER_ID")) {
                String id = extractValue(item, "ORDER_ID");
                String lat = extractValue(item, "LATITUDE");
                String lon = extractValue(item, "LONGITUDE");
                String type = extractValue(item, "DELIVERY_TYPE");
                if (id != null && lat != null && lon != null && type != null) {
                    orders.add(new Order(id, 
                                      Double.parseDouble(lat), 
                                      Double.parseDouble(lon), 
                                      type));
                }
            }
        }
    }
    
    private static String extractValue(String json, String key) {
        int start = json.indexOf("\"" + key + "\":");
        if (start == -1) return null;
        start = json.indexOf("\"", start + key.length() + 2) + 1;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private static String minutesToTimeString(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        String ampm = hours >= 12 ? "PM" : "AM";
        hours = hours > 12 ? hours - 12 : hours;
        return String.format("%d:%02d %s", hours, mins, ampm);
    }

    private static void createDistanceMatrix() {
        int totalLocations = orders.size() + 1;
        distanceMatrix = new double[totalLocations][totalLocations];

        for (int i = 0; i < orders.size(); i++) {
            distanceMatrix[0][i+1] = calculateDistance(OFFICE_LAT, OFFICE_LON, 
                                                     orders.get(i).getLatitude(), 
                                                     orders.get(i).getLongitude());
            distanceMatrix[i+1][0] = distanceMatrix[0][i+1];
        }

        for (int i = 0; i < orders.size(); i++) {
            for (int j = i+1; j < orders.size(); j++) {
                double distance = calculateDistance(
                    orders.get(i).getLatitude(), orders.get(i).getLongitude(),
                    orders.get(j).getLatitude(), orders.get(j).getLongitude()
                );
                distanceMatrix[i+1][j+1] = distance;
                distanceMatrix[j+1][i+1] = distance;
            }
        }

        System.out.println("\nDistance Matrix (in km):");
        System.out.print("          ");
        System.out.print("OFFICE    ");
        for (int i = 0; i < orders.size(); i++) {
            System.out.printf("%-10s", orders.get(i).getOrderId());
        }
        System.out.println();
        
        for (int i = 0; i < totalLocations; i++) {
            System.out.printf("%-10s", i == 0 ? "OFFICE" : orders.get(i-1).getOrderId());
            for (int j = 0; j < totalLocations; j++) {
                System.out.printf("%-10.2f", distanceMatrix[i][j]);
            }
            System.out.println();
        }
    }

    private static void assignDeliveries() {

        List<Order> officeOrders = new ArrayList<>();
        List<Order> homeOrders = new ArrayList<>();
        
        for (Order order : orders) {
            if (order.getDeliveryType().equals("Office")) {
                officeOrders.add(order);
            } else {
                homeOrders.add(order);
            }
        }

        List<DeliveryBoy> morningBoys = getDeliveryBoysByShift("Morning");
        List<DeliveryBoy> eveningBoys = getDeliveryBoysByShift("Evening");

        Comparator<DeliveryBoy> byVehicle = (a, b) -> b.getVehicleType().compareTo(a.getVehicleType());
        morningBoys.sort(byVehicle);
        eveningBoys.sort(byVehicle);

        assignShiftDeliveries(morningBoys, officeOrders, homeOrders, MORNING_SHIFT_START, MORNING_SHIFT_END);

        assignShiftDeliveries(eveningBoys, officeOrders, homeOrders, EVENING_SHIFT_START, EVENING_SHIFT_END);
    }

    private static void assignShiftDeliveries(List<DeliveryBoy> boys, List<Order> officeOrders, 
                                            List<Order> homeOrders, int shiftStart, int shiftEnd) {
        if (boys.isEmpty()) return;

        Map<String, Integer> assignmentCount = new HashMap<>();
        for (DeliveryBoy boy : boys) {
            assignmentCount.put(boy.getDeliveryBoyId(), 0);
        }

        Map<String, double[]> currentPositions = new HashMap<>();
        for (DeliveryBoy boy : boys) {
            currentPositions.put(boy.getDeliveryBoyId(), new double[]{OFFICE_LAT, OFFICE_LON});
        }

        Map<String, Integer> currentTimes = new HashMap<>();
        for (DeliveryBoy boy : boys) {
            currentTimes.put(boy.getDeliveryBoyId(), shiftStart);
        }
        
        for (Order order : officeOrders) {
            if (isOrderAssigned(order.getOrderId())) continue;
            
            DeliveryBoy bestBoy = findBestDeliveryBoy(order, boys, currentPositions, 
                                                     currentTimes, assignmentCount);
            if (bestBoy != null) {
                assignOrder(order, bestBoy, currentPositions, currentTimes, assignmentCount);
            }
        }

        for (Order order : homeOrders) {
            if (isOrderAssigned(order.getOrderId())) continue;
            
            DeliveryBoy bestBoy = findBestDeliveryBoy(order, boys, currentPositions, 
                                                     currentTimes, assignmentCount);
            if (bestBoy != null) {
                assignOrder(order, bestBoy, currentPositions, currentTimes, assignmentCount);
            }
        }
    }

    private static DeliveryBoy findBestDeliveryBoy(Order order, List<DeliveryBoy> boys,
                                                  Map<String, double[]> currentPositions,
                                                  Map<String, Integer> currentTimes,
                                                  Map<String, Integer> assignmentCount) {
        DeliveryBoy bestBoy = null;
        double shortestTime = Double.MAX_VALUE;
        
        for (DeliveryBoy boy : boys) {
            double[] pos = currentPositions.get(boy.getDeliveryBoyId());
            int currentTime = currentTimes.get(boy.getDeliveryBoyId());
            
            double distance = calculateDistance(pos[0], pos[1], order.getLatitude(), order.getLongitude());
            int travelTime = calculateTravelTime(distance, boy.getVehicleType());
            int deliveryTime = currentTime + travelTime;

            if (canAssignOrder(order, boy, currentTime, distance) && 
                isWithinDeliveryWindow(order, deliveryTime)) {

                double score = distance * (1 + assignmentCount.get(boy.getDeliveryBoyId()) * 0.1);
                
                if (score < shortestTime) {
                    shortestTime = score;
                    bestBoy = boy;
                }
            }
        }
        
        return bestBoy;
    }

    private static void assignOrder(Order order, DeliveryBoy boy, Map<String, double[]> currentPositions,
                                  Map<String, Integer> currentTimes, Map<String, Integer> assignmentCount) {
        double[] pos = currentPositions.get(boy.getDeliveryBoyId());
        int currentTime = currentTimes.get(boy.getDeliveryBoyId());
        
        double distance = calculateDistance(pos[0], pos[1], order.getLatitude(), order.getLongitude());
        int travelTime = calculateTravelTime(distance, boy.getVehicleType());
        int endTime = currentTime + travelTime;

        assignments.add(new Assignment(order.getOrderId(), boy.getDeliveryBoyId(),
                                     currentTime, endTime, distance));

        currentPositions.put(boy.getDeliveryBoyId(), 
                            new double[]{order.getLatitude(), order.getLongitude()});
        currentTimes.put(boy.getDeliveryBoyId(), endTime + HANDLING_TIME);
        assignmentCount.put(boy.getDeliveryBoyId(), 
                           assignmentCount.get(boy.getDeliveryBoyId()) + 1);
    }

    private static boolean isOrderAssigned(String orderId) {
        for (Assignment a : assignments) {
            if (a.orderId.equals(orderId)) return true;
        }
        return false;
    }

    private static boolean canAssignOrder(Order order, DeliveryBoy boy, int currentTime, double distance) {
        int travelTime = calculateTravelTime(distance, boy.getVehicleType());
        int deliveryTime = currentTime + travelTime;

        if (order.getDeliveryType().equals("Office")) {
            if (deliveryTime < OFFICE_START || deliveryTime > OFFICE_END) return false;
        } else {
            if (deliveryTime < HOME_START || deliveryTime > HOME_END) return false;
        }

        int shiftEnd = boy.getShift().equals("Morning") ? MORNING_SHIFT_END : EVENING_SHIFT_END;
        return (deliveryTime + HANDLING_TIME) <= shiftEnd;
    }

    private static int calculateTravelTime(double distance, String vehicleType) {
        int speed = vehicleType.equals("Bike") ? BIKE_SPEED : CYCLE_SPEED;
        return (int) Math.ceil((distance / speed) * 60);
    }

    private static boolean isWithinDeliveryWindow(Order order, int deliveryTime) {
        if (order.getDeliveryType().equals("Office")) {
            return deliveryTime >= OFFICE_START && deliveryTime <= OFFICE_END;
        } else {
            return deliveryTime >= HOME_START && deliveryTime <= HOME_END;
        }
    }

    private static void printAssignmentTable() {
        System.out.println("\nDelivery Assignment Table:");
        System.out.printf("%-10s %-10s %-10s %-12s %-12s %-12s %-12s %-15s %-15s\n",
                "Order", "Order", "Delivery", "Delivery", "Travel", "Travel", "Travel", "Travel Time", "Delivery End");
        System.out.printf("%-10s %-10s %-10s %-12s %-12s %-12s %-12s %-15s %-15s\n",
                "ID", "Type", "Boy ID", "Boy Shift", "Start", "End", "Distance", "(in minutes)", "Time");
        System.out.println("-".repeat(110));

        for (Assignment a : assignments) {
            Order order = getOrderById(a.orderId);
            DeliveryBoy boy = getDeliveryBoyById(a.deliveryBoyId);

            String endTime = calculateEndTime(a.startTime, a.endTime);
            
            System.out.printf("%-10s %-10s %-10s %-12s %-12s %-12s %-12.2f %-15d %-15s\n",
                    order.getOrderId(),
                    order.getDeliveryType(),
                    boy.getDeliveryBoyId(),
                    boy.getShift(),
                    formatTime(a.startTime),
                    formatTime(a.endTime),
                    a.distance,
                    calculateTravelTime(a.distance, boy.getVehicleType()),
                    endTime);
        }
    }

    private static void printDeliveryBoySummary() {
        System.out.println("\nDelivery Boy Summary:");
        System.out.printf("%-15s %-15s %-20s %-20s %-20s\n",
                "Delivery Boy", "Delivery Boy", "Total Distance", "Total Travel", "Total Deliveries");
        System.out.printf("%-15s %-15s %-20s %-20s %-20s\n",
                "ID", "Shift", "Travel", "Time", "Assigned");
        System.out.println("-".repeat(90));

        for (DeliveryBoy boy : deliveryBoys) {
            int totalDeliveries = 0;
            double totalDistance = 0;
            int totalTravelTime = 0;

            for (Assignment a : assignments) {
                if (a.deliveryBoyId.equals(boy.getDeliveryBoyId())) {
                    totalDeliveries++;
                    totalDistance += a.distance;
                    totalTravelTime += calculateTravelTime(a.distance, boy.getVehicleType());
                }
            }

            System.out.printf("%-15s %-15s %-20.2f %-20d %-20d\n",
                    boy.getDeliveryBoyId(),
                    boy.getShift(),
                    totalDistance,
                    totalTravelTime,
                    totalDeliveries);
        }
    }

    private static void printFinalSummary() {
        System.out.println("\nSummary Details:");
        System.out.printf("%-50s %-20s\n", "Summary Details", "Value");
        System.out.println("-".repeat(70));

        int totalAssigned = assignments.size();
        int totalUnassigned = orders.size() - totalAssigned;
        
        double totalDistance = 0;
        int totalTravelTime = 0;
        
        for (Assignment a : assignments) {
            totalDistance += a.distance;
            DeliveryBoy boy = getDeliveryBoyById(a.deliveryBoyId);
            totalTravelTime += calculateTravelTime(a.distance, boy.getVehicleType());
        }

        double avgDistance = totalDistance / deliveryBoys.size();
        int avgTravelTime = totalTravelTime / deliveryBoys.size();

        System.out.printf("%-50s %-20d\n", "Total Assigned Orders", totalAssigned);
        System.out.printf("%-50s %-20d\n", "Total Unassigned Orders", totalUnassigned);
        System.out.printf("%-50s %-20.2f\n", "Average distance traveled by all delivery boys", avgDistance);
        System.out.printf("%-50s %-20.2f\n", "Total distance traveled by all delivery boys", totalDistance);
        System.out.printf("%-50s %-20d\n", "Average travel time per delivery boy wise", avgTravelTime);
    }

    private static String formatTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        String period = "AM";
        
        if (hours >= 12) {
            period = "PM";
            if (hours > 12) {
                hours -= 12;
            }
        }
        if (hours == 0) {
            hours = 12;
        }
        
        return String.format("%02d:%02d %s", hours, mins, period);
    }

    private static String calculateEndTime(int startMinutes, int travelMinutes) {
        int totalMinutes = startMinutes + travelMinutes + 15; 
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        String ampm = hours >= 12 ? "PM" : "AM";
        hours = hours > 12 ? hours - 12 : hours;
        return String.format("%02d:%02d %s", hours, minutes, ampm);
    }

    private static Order getOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) return order;
        }
        return null;
    }

    private static DeliveryBoy getDeliveryBoyById(String boyId) {
        for (DeliveryBoy boy : deliveryBoys) {
            if (boy.getDeliveryBoyId().equals(boyId)) return boy;
        }
        return null;
    }

    private static class PerformanceMetrics {
        int totalOrders;
        int assignedOrders;
        int unassignedOrders;
        double totalDistance;
        int totalTravelTime;
        Map<String, Integer> deliveriesPerBoy;
        Map<String, Double> distancePerBoy;
        Map<String, Integer> timePerBoy;
        
        PerformanceMetrics() {
            deliveriesPerBoy = new HashMap<>();
            distancePerBoy = new HashMap<>();
            timePerBoy = new HashMap<>();
        }
    }

    private static void validateAndPrintInputData() {
        System.out.println("\n=== Input Validation Report ===");
        System.out.println("Total Delivery Boys: " + deliveryBoys.size());
        
        // Count shifts and vehicle types
        int morningShift = 0, eveningShift = 0;
        int bikes = 0, cycles = 0;
        
        for (DeliveryBoy boy : deliveryBoys) {
            if (boy.getShift().equals("Morning")) morningShift++;
            else eveningShift++;
            
            if (boy.getVehicleType().equals("Bike")) bikes++;
            else cycles++;
        }
        
        System.out.println("\nDelivery Boy Distribution:");
        System.out.println("Morning Shift: " + morningShift);
        System.out.println("Evening Shift: " + eveningShift);
        System.out.println("Bikes: " + bikes);
        System.out.println("Cycles: " + cycles);
        
        System.out.println("\nTotal Orders: " + orders.size());
        int officeOrders = 0, homeOrders = 0;
        
        for (Order order : orders) {
            if (order.getDeliveryType().equals("Office")) officeOrders++;
            else homeOrders++;
        }
        
        System.out.println("Office Deliveries: " + officeOrders);
        System.out.println("Home Deliveries: " + homeOrders);
        
        // Validate coordinates
        System.out.println("\nValidating Coordinates...");
        boolean validCoordinates = true;
        for (Order order : orders) {
            if (order.getLatitude() < -90 || order.getLatitude() > 90 ||
                order.getLongitude() < -180 || order.getLongitude() > 180) {
                System.out.println("Invalid coordinates for order: " + order.getOrderId());
                validCoordinates = false;
            }
        }
        if (validCoordinates) {
            System.out.println("All coordinates are valid.");
        }
        
        System.out.println("\n==============================\n");
    }

    private static void printDetailedAssignmentAnalysis() {
        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.totalOrders = orders.size();
        metrics.assignedOrders = assignments.size();
        metrics.unassignedOrders = orders.size() - assignments.size();
        
        System.out.println("\n=== Detailed Assignment Analysis ===");
        
        // Initialize metrics for each delivery boy
        for (DeliveryBoy boy : deliveryBoys) {
            metrics.deliveriesPerBoy.put(boy.getDeliveryBoyId(), 0);
            metrics.distancePerBoy.put(boy.getDeliveryBoyId(), 0.0);
            metrics.timePerBoy.put(boy.getDeliveryBoyId(), 0);
        }
        
        // Calculate metrics
        for (Assignment a : assignments) {
            DeliveryBoy boy = getDeliveryBoyById(a.deliveryBoyId);
            metrics.totalDistance += a.distance;
            int travelTime = calculateTravelTime(a.distance, boy.getVehicleType());
            metrics.totalTravelTime += travelTime;
            
            metrics.deliveriesPerBoy.put(a.deliveryBoyId, 
                                        metrics.deliveriesPerBoy.get(a.deliveryBoyId) + 1);
            metrics.distancePerBoy.put(a.deliveryBoyId, 
                                      metrics.distancePerBoy.get(a.deliveryBoyId) + a.distance);
            metrics.timePerBoy.put(a.deliveryBoyId, 
                                  metrics.timePerBoy.get(a.deliveryBoyId) + travelTime);
        }
        
        // Print detailed analysis
        System.out.println("\nWorkload Distribution Analysis:");
        System.out.println("--------------------------------");
        for (DeliveryBoy boy : deliveryBoys) {
            String id = boy.getDeliveryBoyId();
            System.out.printf("\nDelivery Boy: %s (%s shift, %s)\n", 
                             id, boy.getShift(), boy.getVehicleType());
            System.out.printf("Deliveries Assigned: %d\n", metrics.deliveriesPerBoy.get(id));
            System.out.printf("Total Distance: %.2f km\n", metrics.distancePerBoy.get(id));
            System.out.printf("Total Travel Time: %d minutes\n", metrics.timePerBoy.get(id));
            
            if (metrics.deliveriesPerBoy.get(id) > 0) {
                double avgDistance = metrics.distancePerBoy.get(id) / metrics.deliveriesPerBoy.get(id);
                System.out.printf("Average Distance per Delivery: %.2f km\n", avgDistance);
            }
        }
        
        System.out.println("\nTime Window Analysis:");
        System.out.println("---------------------");
        int earlyMorning = 0, officePeak = 0, evening = 0;
        
        for (Assignment a : assignments) {
            int startTime = a.startTime;
            if (startTime < OFFICE_START) earlyMorning++;
            else if (startTime < OFFICE_END) officePeak++;
            else evening++;
        }
        
        System.out.println("Early Morning Deliveries (7AM-10AM): " + earlyMorning);
        System.out.println("Office Hours Deliveries (10AM-6PM): " + officePeak);
        System.out.println("Evening Deliveries (6PM-9PM): " + evening);
        
        System.out.println("\nEfficiency Metrics:");
        System.out.println("------------------");
        System.out.printf("Assignment Rate: %.2f%%\n", 
                         (metrics.assignedOrders * 100.0) / metrics.totalOrders);
        System.out.printf("Average Distance per Delivery: %.2f km\n", 
                         metrics.totalDistance / metrics.assignedOrders);
        System.out.printf("Average Time per Delivery: %.2f minutes\n", 
                         (double)metrics.totalTravelTime / metrics.assignedOrders);
        
        System.out.println("\n===============================\n");
    }

    public static void main(String[] args) {
        String jsonInput = "{\n" +
            "  \"deliveryBoyz\": [\n" +
            "    {\"DELIVERY_BOY_ID\": \"DB_1\", \"SHIFT\": \"Morning\", \"VEHICLE_TYPE\": \"Cycle\"},\n" +
            "    {\"DELIVERY_BOY_ID\": \"DB_2\", \"SHIFT\": \"Evening\", \"VEHICLE_TYPE\": \"Bike\"},\n" +
            "    {\"DELIVERY_BOY_ID\": \"DB_3\", \"SHIFT\": \"Evening\", \"VEHICLE_TYPE\": \"Cycle\"},\n" +
            "    {\"DELIVERY_BOY_ID\": \"DB_4\", \"SHIFT\": \"Morning\", \"VEHICLE_TYPE\": \"Cycle\"},\n" +
            "    {\"DELIVERY_BOY_ID\": \"DB_5\", \"SHIFT\": \"Morning\", \"VEHICLE_TYPE\": \"Bike\"},\n" +
            "    {\"DELIVERY_BOY_ID\": \"DB_6\", \"SHIFT\": \"Morning\", \"VEHICLE_TYPE\": \"Bike\"}\n" +
            "  ],\n" +
            "  \"deliveryOrders\": [\n" +
            "    {\"ORDER_ID\": \"ORDER_1\", \"LATITUDE\": \"17.073291\", \"LONGITUDE\": \"74.637762\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_2\", \"LATITUDE\": \"16.664235\", \"LONGITUDE\": \"74.495559\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_3\", \"LATITUDE\": \"16.785015\", \"LONGITUDE\": \"74.773053\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_4\", \"LATITUDE\": \"16.925529\", \"LONGITUDE\": \"74.576961\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_5\", \"LATITUDE\": \"16.891228\", \"LONGITUDE\": \"74.778013\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_6\", \"LATITUDE\": \"16.886755\", \"LONGITUDE\": \"74.519378\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_7\", \"LATITUDE\": \"16.745693\", \"LONGITUDE\": \"74.749093\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_8\", \"LATITUDE\": \"17.067329\", \"LONGITUDE\": \"74.660192\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_9\", \"LATITUDE\": \"16.768221\", \"LONGITUDE\": \"74.569101\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_10\", \"LATITUDE\": \"16.824578\", \"LONGITUDE\": \"74.611913\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_11\", \"LATITUDE\": \"16.919216\", \"LONGITUDE\": \"74.558308\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_12\", \"LATITUDE\": \"16.749038\", \"LONGITUDE\": \"74.500377\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_13\", \"LATITUDE\": \"16.709103\", \"LONGITUDE\": \"74.513011\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_14\", \"LATITUDE\": \"16.778022\", \"LONGITUDE\": \"74.690549\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_15\", \"LATITUDE\": \"16.780865\", \"LONGITUDE\": \"74.751831\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_16\", \"LATITUDE\": \"16.682763\", \"LONGITUDE\": \"74.492403\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_17\", \"LATITUDE\": \"17.058952\", \"LONGITUDE\": \"74.546290\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_18\", \"LATITUDE\": \"16.796223\", \"LONGITUDE\": \"74.461676\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_19\", \"LATITUDE\": \"16.819086\", \"LONGITUDE\": \"74.779895\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_20\", \"LATITUDE\": \"17.041459\", \"LONGITUDE\": \"74.645582\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_21\", \"LATITUDE\": \"16.864573\", \"LONGITUDE\": \"74.703257\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_22\", \"LATITUDE\": \"16.834380\", \"LONGITUDE\": \"74.359623\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_23\", \"LATITUDE\": \"16.779697\", \"LONGITUDE\": \"74.758793\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_24\", \"LATITUDE\": \"17.067473\", \"LONGITUDE\": \"74.544441\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_25\", \"LATITUDE\": \"16.826233\", \"LONGITUDE\": \"74.641884\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_26\", \"LATITUDE\": \"16.931566\", \"LONGITUDE\": \"74.496221\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_27\", \"LATITUDE\": \"16.799722\", \"LONGITUDE\": \"74.467349\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_28\", \"LATITUDE\": \"16.646604\", \"LONGITUDE\": \"74.545624\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_29\", \"LATITUDE\": \"17.062295\", \"LONGITUDE\": \"74.500247\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_30\", \"LATITUDE\": \"16.824413\", \"LONGITUDE\": \"74.451632\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_31\", \"LATITUDE\": \"16.681795\", \"LONGITUDE\": \"74.654196\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_32\", \"LATITUDE\": \"16.950797\", \"LONGITUDE\": \"74.723640\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_33\", \"LATITUDE\": \"16.714588\", \"LONGITUDE\": \"74.582319\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_34\", \"LATITUDE\": \"16.646424\", \"LONGITUDE\": \"74.526767\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_35\", \"LATITUDE\": \"16.769632\", \"LONGITUDE\": \"74.670799\", \"DELIVERY_TYPE\": \"Office\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_36\", \"LATITUDE\": \"17.003706\", \"LONGITUDE\": \"74.641893\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_37\", \"LATITUDE\": \"16.814765\", \"LONGITUDE\": \"74.476540\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_38\", \"LATITUDE\": \"16.927585\", \"LONGITUDE\": \"74.735472\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_39\", \"LATITUDE\": \"16.945758\", \"LONGITUDE\": \"74.498034\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_40\", \"LATITUDE\": \"17.069102\", \"LONGITUDE\": \"74.520831\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_41\", \"LATITUDE\": \"16.793879\", \"LONGITUDE\": \"74.675980\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_42\", \"LATITUDE\": \"16.971659\", \"LONGITUDE\": \"74.403849\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_43\", \"LATITUDE\": \"16.695632\", \"LONGITUDE\": \"74.524749\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_44\", \"LATITUDE\": \"16.701953\", \"LONGITUDE\": \"74.646826\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_45\", \"LATITUDE\": \"16.870223\", \"LONGITUDE\": \"74.557984\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_46\", \"LATITUDE\": \"16.790800\", \"LONGITUDE\": \"74.474970\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_47\", \"LATITUDE\": \"16.796832\", \"LONGITUDE\": \"74.509894\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_48\", \"LATITUDE\": \"16.788751\", \"LONGITUDE\": \"74.481211\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_49\", \"LATITUDE\": \"17.068968\", \"LONGITUDE\": \"74.575153\", \"DELIVERY_TYPE\": \"Home\"},\n" +
            "    {\"ORDER_ID\": \"ORDER_50\", \"LATITUDE\": \"16.864510\", \"LONGITUDE\": \"74.738151\", \"DELIVERY_TYPE\": \"Home\"}\n" +
            "  ]\n" +
            "}";

        try {
            System.out.println("=== Delivery Assignment System ===\n");
            
            // Parse input data
            System.out.println("Parsing input data...");
            String deliveryBoyzStr = jsonInput;
            int startDB = deliveryBoyzStr.indexOf("\"deliveryBoyz\":");
            int endDB = deliveryBoyzStr.indexOf("\"deliveryOrders\":");
            String dbJson = deliveryBoyzStr.substring(startDB, endDB);
            parseDeliveryBoys(dbJson);

            String ordersJson = deliveryBoyzStr.substring(endDB);
            parseOrders(ordersJson);

            // Validate input data
            validateAndPrintInputData();
            
            // Create distance matrix
            System.out.println("Calculating distances...");
            createDistanceMatrix();
            
            // Assign deliveries
            System.out.println("\nAssigning deliveries...");
            long startTime = System.currentTimeMillis();
            assignDeliveries();
            long endTime = System.currentTimeMillis();
            System.out.printf("\nAssignment completed in %.2f seconds\n", 
                             (endTime - startTime) / 1000.0);
            
            // Print results
            System.out.println("\n=== Results ===");
            printAssignmentTable();
            printDeliveryBoySummary();
            printFinalSummary();
            
            // Print detailed analysis
            printDetailedAssignmentAnalysis();
            
        } catch (Exception e) {
            System.err.println("\nError occurred during execution:");
            System.err.println("--------------------------------");
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
