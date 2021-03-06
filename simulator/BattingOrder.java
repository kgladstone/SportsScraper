/*
 * Object that represents a team
 * Stores the team's lineup, runs scored, hits in game
 * 
 * Created by Keith Gladstone, 2015
 */
public class BattingOrder {
    private Batter[] lineup;
    private int index;
    private int atBatIndex;
    private String teamName;
    private int runs;
    private int hits;
    
    public BattingOrder(String teamName, int size)
    {
        lineup = new Batter[size];
        this.teamName = teamName;
        index = 0;
        atBatIndex = 0;
        runs = 0;
        hits = 0;
    }
    
    public BattingOrder(String file, String teamName, int size)
    {
        lineup = new Batter[size];
        this.teamName = teamName;
        index = 0;
        atBatIndex = 0;
        runs = 0;
        
        // read in the data from csv file
        String path = "../data/" + file; 
        In in = new In(path);
        StdOut.println("Reading players from " + path);
        int nameField = 0;
        int avgField = 3;
        int ABField = 4;
        int hitsField = 6;
        int doublesField = 7;
        int triplesField = 8;
        int HRField = 9;
        boolean yesToAll = true; //If true, add first X batters
            
        String header = in.readLine();
        
        // Parse CSV file
        for (int i = 0; i < size; i++) {
            if (in.isEmpty())
                break;
            String line = in.readLine();
            String[] tokens = line.split(",");
            String name = tokens[nameField];
            Double AVG = Double.parseDouble(tokens[avgField]);
            int AB = Integer.parseInt(tokens[ABField]);
            int hits = Integer.parseInt(tokens[hitsField]);
            int doubles = Integer.parseInt(tokens[doublesField]);
            int triples = Integer.parseInt(tokens[triplesField]);
            int HR = Integer.parseInt(tokens[HRField]);
            
            Batter batter = new Batter(name, AVG);
            batter.setHittingStats(AB, hits, doubles, triples, HR);

            if (yesToAll) 
                add(batter);
            else
            {
                StdOut.println("Add " + name + " to lineup? (y/n)");
                if (StdIn.readLine().equals("y"))
                    add(batter);
                else
                    i--;
            }
        }

    }
    
    public void setBatterAtBat(int start)
    {
        atBatIndex += start - 1;
    }
    public Batter batterAtBat()
    {
        StdOut.println(atBatIndex + 1);
        Batter atBat = get(atBatIndex);
        atBatIndex = (atBatIndex + 1) % index;
        return atBat;
    }
    
    public void add(Batter batter)
    {
        if (index < lineup.length) {
            lineup[index] = batter;
            index++;
        }
        else
            throw new IndexOutOfBoundsException("Too many players in lineup");
    }
    
    /*
     * Return batter at lineup
     */
    public Batter get(int i)
    {
        if (i < index) 
            return lineup[i];
        else
            throw new IndexOutOfBoundsException("Invalid batting order number");
    }
    
    public String teamName()
    {
        return teamName;
    }
    
    public int getRuns()
    {
        return runs;
    }
    
    public int getHits()
    {
        return hits;
    }
    
    public void addRuns(int runs)
    {
        this.runs += runs;
    }
    
    public void addHits(int hits)
    {
        this.hits += hits;
    }
    
    public String toString()
    {
        String result = teamName + ":\n";
        for (int i = 0; i < index; i++)
        {
            result += get(i) + "\n";
        }
        return result;
    }

        
}