import java.util.ArrayList;
import java.util.Random;

public class CreateThread {

    private Random random = new Random();
    private ArrayList<Integer> list1 = new ArrayList<Integer>();
    private ArrayList<Integer> list2 = new ArrayList<Integer>();

    //İki thread birbirini beklemeden calışması için bu object'leri oluşturduk.
    //Bu seyede iki thread bir birini beklemeden işlemleri yapacak ve toplam işlem süresini kısaltacaklar.
    private Object object1 = new Object();
    private Object object2 = new Object();

    public void addValueToList1() throws InterruptedException {
        //Methot'a synchronized keyword'ünü eklemedik.
        //Çünkü method içerisine ekleyerek iki thraed'in ayrı ayrı çalışmasını sağladık.
        //object1 anahtarına sahip olan thread bu sınıfa gırebılır anlamına geliyor.
        synchronized (object1){
            list1.add(random.nextInt(100));
            Thread.sleep(1);
        }
    }

    public void addValueToList2() throws InterruptedException {
        //Methot'a synchronized keyword'ünü eklemedik.
        //Çünkü method içerisine ekleyerek iki thraed'in ayrı ayrı çalışmasını sağladık.
        //object2 anahtarına sahip olan thread bu sınıfa gırebılır anlamına geliyor.
        synchronized (object2){
            list2.add(random.nextInt(100));
            Thread.sleep(1);
        }
    }

    public void runToAddValue() {
        for (int i = 0; i < 1000; i++) {
            try {
                addValueToList1();
                addValueToList2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void runWithThread() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                runToAddValue();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                runToAddValue();
            }
        });

        long startTime = System.currentTimeMillis();
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("List1 size: " + list1.size() + " List2 size: " + list2.size());
        long endTime = System.currentTimeMillis();

        System.out.println("toplam işlem süresi: " + (endTime - startTime) + " mili saniye");

    }

}
