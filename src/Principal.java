
import java.math.BigInteger;

/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplinas: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 * Baseado nos slides 70 da aula do dia 22/09/2017 
 * Algoritmo de Karatsuba: versão final, n arbitrário
 */

/**
 * @author Osmar de Oliveira Braz Junior
 */
public class Principal {

    /**
     * O teto (ceiling) de um número real x é o resultado do arredondamento de x
     * para cima. Em outras palavras, o teto de x é o único número inteiro j tal
     * que j−1<x<=j
     * Ex. O teto de 3.9 é 4
     *
     * @param x Numero real a ser cálculado o teto.
     * @return um valor inteiro com o teto de x.
     */
    public static int teto(double x) {
        //Pego a parte inteira de x;
        int parteInteira = (int) x;
        //Pego a parte fracionária de x
        double parteFracionaria = x - parteInteira;
        //Retorno x subtraindo a parte fracionaria e adiciona 1;
        return (int) (x - parteFracionaria) + 1;
    }

    /**
     * O piso (= floor) de um número real x é o resultado do arredondamento de x para baixo. 
     * Em outras palavras, o piso de x é o único número inteiro i tal que 
     * i<=x<i+1.
     * Ex. O piso de 3.9 é 3
     * 
     * @param x Numero real a ser cálculado o piso.
     * @return um valor inteiro com o piso de x.
     */
    public static int piso(double x) {
        //Pego a parte inteira de x
        int parteInteira = (int) x;
        //Pego a parte fracionária de x
        double parteFracionaria = x - parteInteira;
        //Retorno x subtraindo a parte fracionaria 
        return (int) (x - parteFracionaria);
    }

    /**
     * Realiza a multiplicação segundo o algoritmo de Karatsuba
     * @param u primeiro valor
     * @param v segundo valor
     * @param n quantidade de digitos
     * @return O resultado da multiplicação de v por n
     */
    public static BigInteger karatsuba(BigInteger u, BigInteger v, int n) {
        //Se n é pequeno multiplique diretamente
        if (n <= 3) {
            return u.multiply(v);
        } else {
            int m = teto(n / 2.0);
            
            //x = a + 2^n b, y = c + 2^n d           
            // shiftRight, calcula o piso de 2^n
            // Recupera o primeiro segmento de u
            // b = u mod 10^m
            BigInteger b = u.shiftRight(m);
            // Recupera o segundo segmento de u
            // a = u /10^m
            BigInteger a = u.subtract(b.shiftLeft(m));
            // Recupera o primeiro segmento de v
            // d = v mod 10^m
            BigInteger d = v.shiftRight(m);
            // Recupera o segundo segmento de v
            // c = v /10^m
            BigInteger c = v.subtract(d.shiftLeft(m));
            //Mutiplica a * c
            BigInteger ac = karatsuba(a, c, m);
            //Mutiplica b * d
            BigInteger bd = karatsuba(b, d, m);
            //Soma a + b e c + d e multiplica os resultados
            //y = KARATSUBA (a + b, c + d, m + 1)
            BigInteger y = karatsuba(a.add(b), c.add(d), m);
            //x =  ac * 10^2m + (y − ac − bd) * 10^m + bd
            BigInteger x = ac.add(y.subtract(ac).subtract(bd).shiftLeft(m)).add(bd.shiftLeft(2 * m));
            return x;
        }
    }

    public static void main(String[] args) {

        String su = "99998888";
        String sv = "77776666";
        
        BigInteger u = new BigInteger(su);
        BigInteger v = new BigInteger(sv);

        int n = Math.max(u.bitLength(), v.bitLength());
      
        BigInteger x = karatsuba(u, v, n);

        System.out.println("n:" + n);        
        System.out.println("u:" + su);        
        System.out.println("v:" + sv);        
        System.out.println("u x v=:"+ x);
        
        //System.out.println("x:" + x);
    }
}