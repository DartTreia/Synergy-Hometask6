import java.util.*;
import java.util.List;
import java.util.function.Function;


public class HomeWork6 {
    /*
    Напиши реализацию интерфейса Playable:
        - Метод String play()
        - Метод String[] playWith(Playable playable)
    Напиши реализацию абстрактного класса Instrument:
        - Класс должен релизовать интерфейс Playable
        - Поля класса - sound:String, times:Integer (колличество посторейний звука) (+ конструктор/геттеры)
        - Реализация List<String> play() - метод возвращает список из одной
            строки сформированной по правилу (sound + " ") * times
        - Реализация List<String> playWith(Playable playable) - метод должен вернуть массив
            результатов игры интсруметов в порядке вызова - сначала класс у которого вызвали метод,
            затем класс который передали в качестве аргумента
    Напиши реализацию классов Guitar и Drum:
        - Классы наследуют класс Instrument
        - Конструкторы классов не должны принимать никакие агрументы
        - sound для Guitar "Трунь" и times 2
        - sound для Drum "Бац" и times 3
    Напиши реализацию класса Orchestra
        - Поля - instruments:List<Instrument> (+геттер)
        - Конструктор класса должен принимать любое число агрументов типа Instrument
            Подстказка: загугли что такое функции с переменным числом агрументов и сделай так же
        - Класс должен реализовать интерфейс Playable
        - Реализация List<String> play() - вызываются методы play всех инструментов оркестра,
            результаты вызовов собираются в спиок и возвращаются
        - Реализация List<String> playWith(Playable playable) - метод должен вернуть массив
            результатов в порядке вызова - сначала класс у которого вызвали метод,
            затем класс уоторый передали в качестве аргумента
 */

    public interface Playable {
        List<String> play();
        List<String> playWith(Playable playable);
    }

    public static abstract class Instrument implements Playable {
        String sound;
        Integer times;
        Instrument(String sound, Integer times){
            this.sound = sound;
            this.times=times;
        }
        @Override
        public List<String> play(){
            ArrayList<String> list = new ArrayList<String>();
            String h="";
            for(int i=0;i<times;i++)
                h+=sound+" ";
            list.add(h);
            return list;
        }
        public List<String> playWith(Playable playable){
            ArrayList<String> list = new ArrayList<>();
                list.add(this.play().get(0));
                list.add(playable.play().get(0));
                return list;
        }
        String getSound(){
            return sound;
        }
        Integer getTimes(){
            return times;
        }

    }

    public static class Guitar extends Instrument {
        Guitar(){
            super("Трунь",2);
        }
        @Override
        public List<String> playWith(Playable playable) {
            ArrayList<String> list = new ArrayList<>();
            list.add(this.play().get(0));
            list.add(playable.play().get(0));
            return list;
        }

    }

    public static class Drum extends Instrument {
        Drum(){
            super("Бац",3);
        }

        @Override
        public List<String> playWith(Playable playable) {
            ArrayList<String> list = new ArrayList<>();
            list.add(this.play().get(0));
            list.add(playable.play().get(0));
            return list;
        }
    }

    public static class Orchestra implements Playable {
        ArrayList<Instrument> instruments= new ArrayList<Instrument>();
        Orchestra(Instrument ... k){
            for(int i=0;i<k.length;i++) {
                instruments.add(k[i]);
            }
        }


        List<Instrument> getInstruments(){
            return instruments;
        }

        @Override
        public List<String> play() {
            ArrayList<String> list = new ArrayList<String>();
            for(int i=0;i<instruments.size();i++){
                list.add(instruments.get(i).play().get(0));
            }

            return list;
        }

        @Override
        public List<String> playWith(Playable playable) {
            ArrayList<String> list = new ArrayList<>();
            for(int i=0;i<instruments.size();i++)
            list.add(this.play().get(i));
            list.add(playable.play().get(0));
            return list;
        }
    }

    public static void main(String[] args) {
        var guitar = new Guitar();
        var drum = new Drum();
        print("Guitar: Гитара создалась", true);
        print("Drum:   Барабан создался", true);
        print("Guitar: play Guitar должно быть " + GUITAR_R, Objects.equals(guitar.play().get(0), GUITAR_R));
        print("Drum:   play Drum должно быть " + DRUM_R, Objects.equals(drum.play().get(0), DRUM_R));
        print("Guitar: playWith Drum первая гитара", Objects.equals(guitar.playWith(drum).get(0), GUITAR_R));
        print("Guitar: playWith Drum последний барабан", Objects.equals(guitar.playWith(drum).get(1), DRUM_R));
        print("Drum:   playWith Guitar первый барабан", Objects.equals(drum.playWith(guitar).get(0), DRUM_R));
        print("Drum:   playWith Guitar последняя гитара", Objects.equals(drum.playWith(guitar).get(1), GUITAR_R));

        var emptyOrchestra = new Orchestra();
        var orchestra = new Orchestra(new Guitar(), new Drum(), new Guitar(), new Drum());
        print("EmptyOrchestra: Пустой оркестр создался", true);
        print("EmptyOrchestra: Инструменты должны быть пустым списком", emptyOrchestra.getInstruments() != null);
        print("Orchestra: Оркестр создался", true);
        print("Orchestra: В оркестре должно быть 4 инструмента", orchestra.getInstruments().size() == 4);
        print("Orchestra: Должны сыграть 4 инструмента", orchestra.play().size() == 4);
        print("Orchestra: Гитара играет первая", Objects.equals(orchestra.play().get(0), GUITAR_R));
        print("Orchestra: Барабан играет второй", Objects.equals(orchestra.play().get(1), DRUM_R));
        print("Orchestra: Гитара играет третья", Objects.equals(orchestra.play().get(2), GUITAR_R));
        print("Orchestra: Барабан играет четвертый", Objects.equals(orchestra.play().get(3), DRUM_R));
        print("Orchestra: Должны сыграть 5 инструментов", orchestra.playWith(new Guitar()).size() == 5);
        print("Orchestra: Гитара играет последняя", Objects.equals(orchestra.playWith(new Guitar()).get(4), GUITAR_R));
    }

    /* Техническая секция - сюда писать ничего не надо */

    private static void print(String condition, Boolean act) {
        Function<String, String> yellow = str -> "\u001B[33m" + str + "\u001B[0m";
        System.out.print( "TEST CASE " + yellow.apply(constLen(condition, 55)));
        if (act) System.out.print("✅"); else System.out.print("❌");
        System.out.println();
    }

    private static String constLen(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        while (len-- - str.length() > 0) sb.append(" ");
        return sb.toString();
    }

    private static final String GUITAR_R = "Трунь Трунь ";
    private static final String DRUM_R = "Бац Бац Бац ";
}