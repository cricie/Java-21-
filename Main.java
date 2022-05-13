import java.util.*;
import java.lang.Math;

public class Main {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        Game game=new Game();
        System.out.print("输入玩家个数：");
        int n=sc.nextInt();
        for(int i=0;i<n;i++){
            Player players=new Player();
            game.add(players);
        }
        game.initialize();
        for(int i=0;i<n;i++){
            System.out.printf("玩家%d的牌是\n",i+1);
            game.game.get(i).print();
        }
        int a=1;
        while(a>0) {
            a=game.dealCards();
        }
        game.judgeResult();
    }
}

class Card{
    String suit;
    int rank;
    public Card(){
    }
    Card(String a){
        this.suit=a;
    }
    Card(int b){
        this.rank=b;
    }
    void to_String(){
        System.out.println(rank+" "+suit);
    }
}

class Poker {
    Card[] card=new Card[52];
    int index=0;
    Poker(){
        for(int i=0;i<52;i++)
            card[i]=new Card();
        int c=0;
        for(int i=0;i<13;i++){
            for(int e=1;e<=4;e++){
                String b="";
                if(e==1)
                    b="黑桃";
                else if(e==2)
                    b="红心";
                else if(e==3)
                    b="方块";
                else if(e==4){
                    b="红桃";
                }
                this.card[c].rank=i*4+e;
                this.card[c].suit=b;
                c++;
            }
        }
    }
    void shuffle(){
        for(int i=0;i<51;i++){
            for(int e=i+1;e<52;e++){
                Random rand=new Random();
                int a=rand.nextInt();
                int b=rand.nextInt();
                if(a>b){
                    Card temp=new Card();
                    temp=card[i];
                    card[i]=card[e];
                    card[e]=temp;
                }
            }
        }
    }
    Card pop(){
        //card[index].to_String();
        Card ch=new Card();
        ch=card[index];
        index=(index+1)%52;
        return ch;
    }
    void print(){
        for(int i=0;i<52;i++){
            System.out.print(card[i].suit+card[i].rank+" ");
            if(i%10==0)
                System.out.printf("\n");
        }
    }
}

class Player {
    String name;
    //Card cards=new Card();
    ArrayList<Card> cards=new ArrayList<Card>();
    Player(){
    }
    void add(Card ch){
        cards.add(ch);
    }
    void print(){
        int n= cards.size();
        for(int i=0;i<n;i++){
            System.out.print(cards.get(i).suit+cards.get(i).rank+" ");
        }
        System.out.printf("\n");
    }
    int decide(Card ch){
        Scanner sc=new Scanner(System.in);
        print();
        System.out.print("按1要牌，按0不要牌");
        int a=sc.nextInt();
        if(a==1)
            add(ch);
        print();
        return a;
    }
    void sort(){
        int n=cards.size();
        for(int i=0;i<n-1;i++){
            int k=0;
            for(int e=i+1;e<n;e++){
                if(cards.get(i).rank>cards.get(e).rank){
                    k=e;
                }
            }
            Collections.swap(cards,i,k);
        }
    }
    int score(){
        int n=cards.size();
        int ans=0;
        for(Card i:cards){
            ans+=i.rank;
        }
        return ans;
    }
}

class Game{
    Poker pai=new Poker();
    int len=0;
    ArrayList<Player> game=new ArrayList<Player>();
    Game(){
    }
    void add(Player ren){
        game.add(ren);
        this.len++;
    }
    void initialize(){
        for(int i=0;i<len;i++){
            for(int e=0;e<2;e++){
                game.get(i).add(pai.pop());
            }
        }
    }
    int dealCards(){
        int b=0;
        for(int i=0;i<len;i++){
            System.out.printf("玩家%d\n",i+1);
            b+=game.get(i).decide(pai.pop());
        }
        if(b==0)
            return 0;
        else
            return 1;
    }
    void judgeResult(){
        int ans=100,index=0,dis=0;
        for(int i=0;i<len;i++){
            int m=game.get(i).score();
            int n=Math.abs(m-21);
            System.out.printf("玩家%d的点数是%d\n",i+1,m);
            if(n<ans){
                ans=n;
                index=i+1;
                dis=m;
            }
        }
        System.out.print("赢家是玩家"+index+" ");
        System.out.print("点数是"+dis);
    }
}