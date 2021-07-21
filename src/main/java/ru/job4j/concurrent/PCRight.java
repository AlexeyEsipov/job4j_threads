package ru.job4j.concurrent;
class QR {
    private int n;
    private boolean valueSet = false;

    synchronized int get() {
        while (!valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Получено: " + n);
        valueSet = false;
        notify();
        return n;
    }

    synchronized void put(int n) {
        while (valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.n = n;
        valueSet = true;
        System.out.println("Отправлено: " + n);
        notify();
    }
}

class ProducerR implements Runnable {
    private QR q;

    public ProducerR(QR q) {
        this.q = q;
        new Thread(this, "Поставщик").start();
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            q.put(i++);
        }
    }
}

class ConsumerR implements  Runnable {
    private QR q;

    public ConsumerR(QR q) {
        this.q = q;
        new Thread(this, "Потребитель").start();
    }

    @Override
    public void run() {
        while (true) {
            q.get();
        }
    }
}

public class PCRight {
    public static void main(String[] args) {
        QR q = new QR();
        new ProducerR(q);
        new ConsumerR(q);
        System.out.println("Для остановки нажмите Ctrl-C");
    }
}
