import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ExperimentsZachet {

        static List<DictionaryEntry> getDictionary() throws FileNotFoundException {
            List<DictionaryEntry> dict = new ArrayList<>();

            Scanner scanner = new Scanner(new File("dictionary.txt"));
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                int pos = line.indexOf(' ');
                if(pos>=0 && line.length()>0)
                {
                    String word = line.substring(0, pos);
                    String translation = line.substring(pos+1);
                    dict.add(new DictionaryEntry(word, translation));
                }
                else
                {
                    break;
                }
            }

            return dict;
        }

        static void simpleQuery(List<DictionaryEntry> dict) throws IOException {
            Scanner scanner = new Scanner(new File("strict-queries.txt"));

            try(PrintStream out = new PrintStream("result-strict-queries.txt")){
                while(scanner.hasNext())
                {
                    String word = scanner.next();
                    var res = dict.stream().filter(dictionaryEntry -> dictionaryEntry.getWord()==word).collect(Collectors.toList());
                    if(res.size()>0)
                    {
                        out.println("запрос: " + word);
                        out.print("найдено:");
                        for (var item: res) {
                            out.print(" "+item.getTranslation());
                        }
                        out.println();
                    }
                }
            }
        }

        static void query(List<DictionaryEntry> dict) throws FileNotFoundException {
            Scanner scanner = new Scanner(new File("full-text-search.txt"));

            try(PrintStream out = new PrintStream("result-full-text-search.txt")){
                while(scanner.hasNext())
                {
                    String word = scanner.next();
                    var res = dict.stream().filter(dictionaryEntry -> dictionaryEntry.getWord().indexOf(word)!=-1).collect(Collectors.toList());
                    if(res.size()>0)
                    {
                        out.println("запрос: " + word);
                        out.print("найдено:");
                        for (var item: res) {
                            out.print(" "+item.getTranslation());
                        }
                        out.println();
                    }
                }
            }
        }

        public static void main(String[] args) throws IOException {
            List<DictionaryEntry> dict = getDictionary();
            simpleQuery(dict);
            query(dict);
        }
    }


