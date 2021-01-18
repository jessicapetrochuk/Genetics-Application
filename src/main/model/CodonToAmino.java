package model;

import java.util.HashMap;
import java.util.Map;

/**Represents all of the codons with the corresponding amino acid they code for during translation */
public class CodonToAmino {
    public static Map<Codon, AminoAcid> map = new HashMap<Codon, AminoAcid>() {{
            put(Codon.UUU, AminoAcid.PHE);
            put(Codon.UUC, AminoAcid.PHE);
            put(Codon.UUA, AminoAcid.LEU);
            put(Codon.UUG, AminoAcid.LEU);
            put(Codon.CUU, AminoAcid.LEU);
            put(Codon.CUC, AminoAcid.LEU);
            put(Codon.CUA, AminoAcid.LEU);
            put(Codon.CUG, AminoAcid.LEU);
            put(Codon.AUU, AminoAcid.ILE);
            put(Codon.AUC, AminoAcid.ILE);
            put(Codon.AUA, AminoAcid.ILE);
            put(Codon.AUG, AminoAcid.MET);
            put(Codon.GUU, AminoAcid.VAL);
            put(Codon.GUC, AminoAcid.VAL);
            put(Codon.GUA, AminoAcid.VAL);
            put(Codon.GUG, AminoAcid.VAL);
            put(Codon.UCU, AminoAcid.SER);
            put(Codon.UCC, AminoAcid.SER);
            put(Codon.UCA, AminoAcid.SER);
            put(Codon.UCG, AminoAcid.SER);
            put(Codon.CCU, AminoAcid.PRO);
            put(Codon.CCC, AminoAcid.PRO);
            put(Codon.CCA, AminoAcid.PRO);
            put(Codon.CCG, AminoAcid.PRO);
            put(Codon.ACU, AminoAcid.THR);
            put(Codon.ACC, AminoAcid.THR);
            put(Codon.ACA, AminoAcid.THR);
            put(Codon.ACG, AminoAcid.THR);
            put(Codon.GCU, AminoAcid.ALA);
            put(Codon.GCC, AminoAcid.ALA);
            put(Codon.GCA, AminoAcid.ALA);
            put(Codon.GCG, AminoAcid.ALA);
            put(Codon.UAU, AminoAcid.TYR);
            put(Codon.UAC, AminoAcid.TYR);
            put(Codon.UAA, AminoAcid.STOP);
            put(Codon.UAG, AminoAcid.STOP);
            put(Codon.CAU, AminoAcid.HIS);
            put(Codon.CAC, AminoAcid.HIS);
            put(Codon.CAA, AminoAcid.GLN);
            put(Codon.CAG, AminoAcid.GLN);
            put(Codon.AAU, AminoAcid.ASN);
            put(Codon.AAC, AminoAcid.ASN);
            put(Codon.AAA, AminoAcid.LYS);
            put(Codon.AAG, AminoAcid.LYS);
            put(Codon.GAU, AminoAcid.ASP);
            put(Codon.GAC, AminoAcid.ASP);
            put(Codon.GAA, AminoAcid.GLU);
            put(Codon.GAG, AminoAcid.GLU);
            put(Codon.UGU, AminoAcid.CYS);
            put(Codon.UGC, AminoAcid.CYS);
            put(Codon.UGA, AminoAcid.STOP);
            put(Codon.UGG, AminoAcid.TRP);
            put(Codon.CGU, AminoAcid.ARG);
            put(Codon.CGC, AminoAcid.ARG);
            put(Codon.CGA, AminoAcid.ARG);
            put(Codon.CGG, AminoAcid.ARG);
            put(Codon.AGU, AminoAcid.SER);
            put(Codon.AGC, AminoAcid.SER);
            put(Codon.AGA, AminoAcid.ARG);
            put(Codon.AGG, AminoAcid.ARG);
            put(Codon.GGU, AminoAcid.GLY);
            put(Codon.GGC, AminoAcid.GLY);
            put(Codon.GGA, AminoAcid.GLY);
            put(Codon.GGG, AminoAcid.GLY);
        }
    };
}
