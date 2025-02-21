package com.example.sem2project;

import com.example.sem2project.Model.Atom;
import javafx.util.Pair;

import java.util.ArrayList;

public class Algorithm {
    public final static String[] substituentsPrefixes = new String[] {"","","di","tri","tetr","pent","hex","hept","oct","non","dec"};
    public final static String[] chainPrefixes = new String[] {"","meth","eth","prop","but","pent","hex","hept","oct","non","dec"};
    public static ArrayList<Atom> compoundAtoms = new ArrayList<Atom>();
    public static ArrayList<Atom> carbons = new ArrayList<>();
    public static ArrayList<Atom> parentChainCarbons = new ArrayList<>();
    public static int parentChainLength = 0;
    private static String compoundName;

    public final static String[] functionalGroupsPriority = new String[]{"Carboxylic acid", "Amide", "Aldehyde", "Ketone", "Alcohol", "Amine", "Alkene", "Alkyne", "Alkane"}; //lower index = higher priority
    public static boolean[] functionalGroupsPresent = new boolean[]{false, false, false, false, false, false, false, false, false};
    public static ArrayList<ArrayList<Integer>> functionalGroupCarbonLocations = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<Integer> bromineLocations=new ArrayList<>(), chlorineLocations=new ArrayList<>(), fluorineLocations=new ArrayList<>(), iodineLocations=new ArrayList<>();
    //9 functional Groups
    //Locations arraylists store the index of the carbon in the compound they are attached to
    public static ArrayList<Atom> visited = new ArrayList<Atom>();
    public static boolean reversenumbers = false;
    public static int priorityGroup=1000;
    public static String carboxylicString="", amideString="", alcoholString="", amineString="", alkeneString="", alkyneString="", alkaneString="";
    public static String functionalGroupString = "";

    public static String generateName() {
        for (Atom i : compoundAtoms) {
            if (i.getAtomType().equals("Carbon")) {
                carbons.add(i);
            }
        }


        parentChainLength = 0;
        findCarbonChainLength(carbons.get(0));

        System.out.println("Parent Chain Length:" + parentChainLength);
        priorityGroup = 1000;
        findFunctionalGroups();
        for (int i=0;i<9;i++){
            if (functionalGroupsPresent[i]) {
                if (i < priorityGroup) {
                    priorityGroup = i;
                }
                if (i!=8) {
                    if (functionalGroupString.equals("")) functionalGroupString += String.format("%s", functionalGroupsPriority[i]);
                    else functionalGroupString += String.format(", %s", functionalGroupsPriority[i]);
                }
            }
        }
        if (priorityGroup==8) functionalGroupString = "None";

        int locationsum = 0;
        if (priorityGroup != 8) {
            //If average of carbon indexes where the priority functional groups are at is greater than the parent chain length/2, reverse the indexes
            //Example: 1,5,6-tribromo is reversed into 1,2,6-tribromo
            for (int i : functionalGroupCarbonLocations.get(priorityGroup)) {
                locationsum += i + 1;
            }
            if (locationsum/functionalGroupCarbonLocations.get(priorityGroup).size() > parentChainLength/2) { //reverse numbers
                reversenumbers = true;
            }
            System.out.printf(String.format("Locationsum: %s, Reversed: %s\n",locationsum, reversenumbers));
        }
        else {
            //If there is no priority group (i.e. priority grp is alkane), reversing is decided by the average of sum of Br, Cl, F, I positions instead
            int reverselocationsum = 0;
            for (int i : bromineLocations) {
                locationsum += i+1;
                reverselocationsum += parentChainLength-i-1;
            }
            for (int i : chlorineLocations) {
                locationsum += i+1;
                reverselocationsum += parentChainLength-i-1;
            }
            for (int i : fluorineLocations) {
                locationsum += i+1;
                reverselocationsum += parentChainLength-i-1;
            }
            for (int i : iodineLocations) {
                locationsum += i+1;
                reverselocationsum += parentChainLength-i-1;
            }

            if (locationsum > reverselocationsum) { //reverse numbers
                reversenumbers = true;
            }
            System.out.printf(String.format("Locationsum: %s, ReverseSum: %s, Reversed: %s\n",locationsum, reverselocationsum, reversenumbers));
        }



        //Processing for Br, Cl, F, I prefixes
        String bromineString="", chlorineString="", fluorineString="", iodineString="";
        if (!bromineLocations.isEmpty()) {
            for (Integer i : bromineLocations) System.out.printf("%s, ", i);
            if (reversenumbers) {
                for (int i = bromineLocations.size()-1; i >=0; i--) {
                    if (i == bromineLocations.size()-1) bromineString += String.format("%s", parentChainLength-bromineLocations.get(i));
                    else bromineString += String.format(",%s", parentChainLength-bromineLocations.get(i));
                }
            }
            else {
                for (int i = 0; i < bromineLocations.size(); i++) {
                    if (i == 0) bromineString += String.format("%s", bromineLocations.get(i)+1);
                    else bromineString += String.format(",%s", bromineLocations.get(i)+1);
                }
            }
            if (priorityGroup==8 && iodineLocations.isEmpty() && fluorineLocations.isEmpty() && chlorineLocations.isEmpty()) bromineString += String.format("-%sbromo",substituentsPrefixes[bromineLocations.size()]);
            else bromineString += String.format("-%sbromo-",substituentsPrefixes[bromineLocations.size()]);
        }
        if (!chlorineLocations.isEmpty()) {
            if (reversenumbers) {
                for (int i = chlorineLocations.size()-1; i >=0; i--) {
                    if (i == chlorineLocations.size()-1) chlorineString += String.format("%s", parentChainLength-chlorineLocations.get(i));
                    else chlorineString += String.format(",%s", parentChainLength-chlorineLocations.get(i));
                }
            }
            else {
                for (int i = 0; i < chlorineLocations.size(); i++) {
                    if (i == 0) chlorineString += String.format("%s", chlorineLocations.get(i)+1);
                    else chlorineString += String.format(",%s", chlorineLocations.get(i)+1);
                }
            }
            if (priorityGroup==8 && iodineLocations.isEmpty() && fluorineLocations.isEmpty()) chlorineString += String.format("-%schloro",substituentsPrefixes[chlorineLocations.size()]);
            else chlorineString += String.format("-%schloro-",substituentsPrefixes[chlorineLocations.size()]);
        }
        if (!fluorineLocations.isEmpty()) {
            if (reversenumbers) {
                for (int i = fluorineLocations.size()-1; i >=0; i--) {
                    if (i == fluorineLocations.size()-1) fluorineString += String.format("%s", parentChainLength-fluorineLocations.get(i));
                    else fluorineString += String.format(",%s", parentChainLength-fluorineLocations.get(i));
                }
            }
            else {
                for (int i = 0; i < fluorineLocations.size(); i++) {
                    if (i == 0) fluorineString += String.format("%s", fluorineLocations.get(i)+1);
                    else fluorineString += String.format(",%s", fluorineLocations.get(i)+1);
                }
            }
            if (priorityGroup==8 && iodineLocations.isEmpty()) fluorineString += String.format("-%sfluoro",substituentsPrefixes[fluorineLocations.size()]);
            else fluorineString += String.format("-%sfluoro-",substituentsPrefixes[fluorineLocations.size()]);
        }
        if (!iodineLocations.isEmpty()) {
            if (reversenumbers) {
                for (int i = iodineLocations.size()-1; i >=0; i--) {
                    if (i == iodineLocations.size()-1) iodineString += String.format("%s", parentChainLength-iodineLocations.get(i));
                    else iodineString += String.format(",%s", parentChainLength-iodineLocations.get(i));
                }
            }
            else {
                for (int i = 0; i < iodineLocations.size(); i++) {
                    if (i == 0) iodineString += String.format("%s", iodineLocations.get(i)+1);
                    else iodineString += String.format(",%s", iodineLocations.get(i)+1);
                }
            }
            if (priorityGroup==8) iodineString += String.format("-%siodo",substituentsPrefixes[iodineLocations.size()]);
            else iodineString += String.format("-%siodo-",substituentsPrefixes[iodineLocations.size()]);
        }

        processCarboxylicAcid();
        processAmide();
        processAlcohol();
        processAmine();
        processAlkane();
        processAlkene();
        processAlkyne();








        switch (priorityGroup) {
            case 8: compoundName = bromineString + chlorineString + fluorineString + iodineString + alkaneString; break;
            case 7: compoundName = bromineString + chlorineString + fluorineString + iodineString + alkaneString + alkyneString; break;
            case 6: compoundName = bromineString + chlorineString + fluorineString + iodineString + alkaneString + alkeneString + alkyneString; break;
            case 5: compoundName = bromineString + chlorineString + fluorineString + iodineString + alkaneString + alkeneString + alkyneString + amineString; break;
            case 4: compoundName = bromineString + chlorineString + fluorineString + iodineString + amineString + alkaneString + alkeneString + alkyneString + alcoholString; break;
            case 2: case 3: compoundName = "Sorry, but this simulation is currently unable to identify this compound's name."; break;
            case 1: compoundName = bromineString + chlorineString + fluorineString + iodineString + amineString + alcoholString + alkaneString + alkeneString + alkyneString + amideString; break;
            case 0: compoundName = bromineString + chlorineString + fluorineString + iodineString + amineString + alcoholString + alkaneString + alkeneString + alkyneString + carboxylicString; break;
        }

        return String.format("IUPAC Name: %s\n\nFunctional groups present: %s",compoundName, functionalGroupString);
    }

    public static void findCarbonChainLength(Atom source) {
        parentChainCarbons.add(source);
        parentChainLength += 1;
        visited.add(source);
        for (Pair<Atom, Integer> i : source.connectedAtoms){
            if (i.getKey().getAtomType().equals("Carbon")) {
                if (!visited.contains(i.getKey())) {
                    visited.add(i.getKey());
                    findCarbonChainLength(i.getKey());
                }
            }
        }
    }

    public static void findFunctionalGroups() {
        functionalGroupCarbonLocations.add(detectCarboxylicAcid());
        functionalGroupCarbonLocations.add(detectAmide());
        functionalGroupCarbonLocations.add(detectAldehyde());
        functionalGroupCarbonLocations.add(detectKetone());
        functionalGroupCarbonLocations.add(detectAlcohol());
        functionalGroupCarbonLocations.add(detectAmine());
        functionalGroupCarbonLocations.add(detectAlkenes());
        functionalGroupCarbonLocations.add(detectAlkynes());
        functionalGroupCarbonLocations.add(detectAlkanes());


        bromineLocations = detectBromine();
        chlorineLocations = detectChlorine();
        fluorineLocations = detectFluorine();
        iodineLocations = detectIodine();


        //Remove alcohol if it is already part of a carboxylic acid group
        for (int i : functionalGroupCarbonLocations.get(0)) {
            if (functionalGroupCarbonLocations.get(4).contains(i)) functionalGroupCarbonLocations.get(4).remove(Integer.valueOf(i));
        }
        if (functionalGroupCarbonLocations.get(4).isEmpty()) functionalGroupsPresent[4] = false;
        //Remove amine if it is already part of an amide gorup
        for (int i : functionalGroupCarbonLocations.get(1)) {
            if (functionalGroupCarbonLocations.get(5).contains(i)) functionalGroupCarbonLocations.get(5).remove(Integer.valueOf(i));
        }
        if (functionalGroupCarbonLocations.get(5).isEmpty()) functionalGroupsPresent[5] = false;


    }

    public static ArrayList<Integer> detectAlkanes() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            if (i.connectedAtomsContainsTypeAndBond("Carbon", 1)) {
                carbonLocations.add(findIndex(i));
                functionalGroupsPresent[8] = true;
            }
        }
        return carbonLocations;
    }
    public static ArrayList<Pair<Atom,Atom>> alkenesVisited = new ArrayList<>();
    public static ArrayList<Integer> detectAlkenes() {
        //For all carbons i--> For all atoms j connected to the carbons--
        //--> if connected atom is a carbon with double bond & not already registered --> add i into carbonLocations
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            for (Pair<Atom, Integer> j : i.connectedAtoms) {
                if (j.getKey().getAtomType().equals("Carbon") && j.getValue() == 2) {
                    if (!alkenesVisited.isEmpty()) {
                        int size = alkenesVisited.size();
                        boolean overallInside = false;
                        for (int k=0;k<size;k++){
                            //is the 2 atoms connected by double bond visited before?
                            //To prevent double counting, we check if the pair (i,j.getKey()) or (j.getKey(), i) is already in alkenesVisited
                            //If overall the pair does not exist at all yet, add it
                            if (alkenesVisited.get(k).getKey().equals(i) && alkenesVisited.get(k).getValue().equals(j.getKey())) {
                                System.out.printf("Alkene already added - %s, %s   (%s, %s, %s)\n", findIndex(i), findIndex(j.getKey()), i, j.getKey(), alkenesVisited.get(k).getKey());
                                overallInside = true;
                            } else if (alkenesVisited.get(k).getKey().equals(j.getKey()) && alkenesVisited.get(k).getValue().equals(i)) {
                                System.out.printf("Alkene already added - %s, %s   (%s, %s, %s)\n", findIndex(i), findIndex(j.getKey()), i, j.getKey(), alkenesVisited.get(k).getKey());
                                overallInside = true;
                            }

                        }
                        if (!overallInside) {
                            carbonLocations.add(findIndex(i));
                            functionalGroupsPresent[6] = true;
                            alkenesVisited.add(new Pair<Atom, Atom>(i, j.getKey()));
                            System.out.printf("New alkeneVisited - %s, %s\n", findIndex(i), findIndex(j.getKey()));
                        }
                    }
                    else {
                        carbonLocations.add(findIndex(i));
                        functionalGroupsPresent[6] = true;
                        alkenesVisited.add(new Pair<Atom,Atom>(i, j.getKey()));
                        System.out.printf("Empty alkeneVisited - %s, %s   (%s, %s)\n", findIndex(i), findIndex(j.getKey()), i, j.getKey());
                    }

                }
            }
        }
        for (Integer i : carbonLocations) System.out.printf("%s, ", i);
        System.out.println();
        return carbonLocations;
    }

    public static ArrayList<Pair<Atom,Atom>> alkynesVisited = new ArrayList<>();
    public static ArrayList<Integer> detectAlkynes() {
        //For all carbons i --> For all atoms j connected to the carbons --> if connected atom is a carbon with triple bond & not already registered --> add i into carbonLocations
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            for (Pair<Atom, Integer> j : i.connectedAtoms) {
                if (j.getKey().getAtomType().equals("Carbon") && j.getValue() == 3) {
                    if (!alkynesVisited.isEmpty()) {
                        int size = alkynesVisited.size();
                        boolean overallInside = false;
                        for (int k=0;k<size;k++){
                            //is the 2 atoms connected by triple bond visited before?
                            //To prevent double counting, we check if the pair (i,j.getKey()) or (j.getKey(), i) is already in alkynesVisited
                            //If overall the pair does not exist at all yet, add it
                            if (alkynesVisited.get(k).getKey().equals(i) && alkynesVisited.get(k).getValue().equals(j.getKey())) {
                                System.out.printf("Alkyne already added - %s, %s   (%s, %s, %s)\n", findIndex(i), findIndex(j.getKey()), i, j.getKey(), alkynesVisited.get(k).getKey());
                                overallInside = true;
                            } else if (alkynesVisited.get(k).getKey().equals(j.getKey()) && alkynesVisited.get(k).getValue().equals(i)) {
                                System.out.printf("Alkyne already added - %s, %s   (%s, %s, %s)\n", findIndex(i), findIndex(j.getKey()), i, j.getKey(), alkynesVisited.get(k).getKey());
                                overallInside = true;
                            }

                        }
                        if (!overallInside) {
                            carbonLocations.add(findIndex(i));
                            functionalGroupsPresent[7] = true;
                            alkynesVisited.add(new Pair<Atom, Atom>(i, j.getKey()));
                            System.out.printf("New alkyneVisited - %s, %s\n", findIndex(i), findIndex(j.getKey()));
                        }
                    }
                    else {
                        carbonLocations.add(findIndex(i));
                        functionalGroupsPresent[7] = true;
                        alkynesVisited.add(new Pair<Atom,Atom>(i, j.getKey()));
                        System.out.printf("Empty alkyneVisited - %s, %s   (%s, %s)\n", findIndex(i), findIndex(j.getKey()), i, j.getKey());
                    }

                }
            }
        }
        for (Integer i : carbonLocations) System.out.printf("%s, ", i);
        System.out.println();
        return carbonLocations;
    }

    public static ArrayList<Integer> detectCarboxylicAcid() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            if (i.connectedAtomsContainsTypeAndBond("Oxygen", 2) && i.connectedAtomsContainsTypeAndBond("Oxygen", 1)) {
                for (Pair<Atom, Integer> j : i.connectedAtoms) {
                    if (j.getKey().getAtomType().equals("Oxygen") && j.getValue()==1) {
                        if (j.getKey().connectedAtomsContainsType("Hydrogen")) {
                            carbonLocations.add(findIndex(i));
                            functionalGroupsPresent[0] = true;
                        }
                    }
                }


            }
        }
        return carbonLocations;
    }

    public static ArrayList<Integer> detectAmine() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            if (i.connectedAtomsContainsType("Nitrogen")) {
                carbonLocations.add(findIndex(i));
                functionalGroupsPresent[5] = true;
            }
        }
        return carbonLocations;
    }

    public static ArrayList<Integer> detectAmide() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            if (i.connectedAtomsContainsTypeAndBond("Oxygen", 2) && i.connectedAtomsContainsType("Nitrogen")) {
                carbonLocations.add(findIndex(i));
                functionalGroupsPresent[1] = true;
            }
        }
        return carbonLocations;
    }

    public static ArrayList<Integer> detectAlcohol() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            for (Pair<Atom, Integer> j : i.connectedAtoms) {
                if (j.getKey().getAtomType().equals("Oxygen") && j.getValue() == 1) {
                    if (j.getKey().connectedAtomsContainsType("Hydrogen")) {
                        carbonLocations.add(findIndex(i));
                        functionalGroupsPresent[4] = true;
                    }
                }
            }
        }
        return carbonLocations;
    }



    public static ArrayList<Integer> detectKetone() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            boolean oxygenPresent = false;
            int carbonsFound = 0;
            for (Pair<Atom,Integer> j : i.connectedAtoms) {

                if (j.getKey().getAtomType().equals("Oxygen") && j.getValue()==2) oxygenPresent = true;
                if (j.getKey().getAtomType().equals("Carbon")) carbonsFound += 1;

            }
            if (oxygenPresent && carbonsFound==2) {
                carbonLocations.add(findIndex(i));
                functionalGroupsPresent[3] = true;
            }
        }
        return carbonLocations;
    }

    public static ArrayList<Integer> detectAldehyde() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            if (i.connectedAtomsContainsTypeAndBond("Oxygen", 2) && i.connectedAtomsContainsType("Hydrogen")) {
                carbonLocations.add(findIndex(i));
                functionalGroupsPresent[2] = true;
            }
        }
        return carbonLocations;
    }

    public static ArrayList<Integer> detectBromine() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            for (Pair <Atom, Integer> j : i.connectedAtoms) {
                if (j.getKey().getAtomType().equals("Bromine")) {
                    carbonLocations.add(findIndex(i));
                }
            }
        }
        return carbonLocations;
    }

    public static ArrayList<Integer> detectChlorine() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            for (Pair <Atom, Integer> j : i.connectedAtoms) {
                if (j.getKey().getAtomType().equals("Chlorine")) {
                    carbonLocations.add(findIndex(i));
                }
            }
        }
        return carbonLocations;
    }
    public static ArrayList<Integer> detectFluorine() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            for (Pair <Atom, Integer> j : i.connectedAtoms) {
                if (j.getKey().getAtomType().equals("Fluorine")) {
                    carbonLocations.add(findIndex(i));
                }
            }
        }
        return carbonLocations;
    }
    public static ArrayList<Integer> detectIodine() {
        ArrayList<Integer> carbonLocations = new ArrayList<>();
        for (Atom i : parentChainCarbons) {
            for (Pair <Atom, Integer> j : i.connectedAtoms) {
                if (j.getKey().getAtomType().equals("Iodine")) {
                    carbonLocations.add(findIndex(i));
                }
            }
        }
        return carbonLocations;
    }

    public static int findIndex(Atom atom) {
        for (int i=0;i<parentChainCarbons.size();i++){
            if (parentChainCarbons.get(i).equals(atom)) return i;
        }
        return -1;
    }

    public static void processCarboxylicAcid() {
        /* Processing for carboxylic acid as main functional group */
        if (priorityGroup == 0) {
            if (functionalGroupsPresent[6]) carboxylicString = String.format("enoic acid");
            if (functionalGroupsPresent[7]) carboxylicString = String.format("ynoic acid");
            if (!(functionalGroupsPresent[6] || functionalGroupsPresent[7])) carboxylicString = String.format("anoic acid");
        }
    }

    public static void processAmide() {
        /* Processing for amide as main functional group */
        if (priorityGroup == 1) {
            if (parentChainLength==1) amideString += "formamide"; //Special case: amide with only 1 carbon in parent chain is called formamide
            if (functionalGroupsPresent[6]) amideString = String.format("enamide", chainPrefixes[parentChainLength]);
            if (functionalGroupsPresent[7]) amideString = String.format("ynamide", chainPrefixes[parentChainLength]);
            if (!(functionalGroupsPresent[6] || functionalGroupsPresent[7])) amideString = String.format("anamide", chainPrefixes[parentChainLength]);
        }
    }

    public static void processAlcohol() {
        /* Processing for alcohol */
        if (functionalGroupsPresent[4]) {
            //Processing for when alcohol is main functional group
            if (priorityGroup == 4) {
                //Add name depending on parent chain --> Example: methane-, ethane-, etc.
                //If no. of alchols == 1 --> name becomes methan-, ethan-, etc.

                //Add locations of alcohol groups --> Example: 1,2,2-  3,4-   etc.
                if (reversenumbers) {
                    for (int i = functionalGroupCarbonLocations.get(4).size() - 1; i >= 0; i--) {
                        if (i == functionalGroupCarbonLocations.get(4).size() - 1) alcoholString += String.format("%s", parentChainLength - functionalGroupCarbonLocations.get(4).get(i));
                        else alcoholString += String.format(",%s", parentChainLength - functionalGroupCarbonLocations.get(4).get(i));
                    }
                }
                else {
                    for (int i = 0; i < functionalGroupCarbonLocations.get(4).size(); i++) {
                        if (i == 0) alcoholString += String.format("%s", functionalGroupCarbonLocations.get(4).get(i) + 1);
                        else alcoholString += String.format(",%s", functionalGroupCarbonLocations.get(4).get(i) + 1);
                    }
                }
                //Add name depending on no. of alchol groups --> Example: -ol, -diol, -triol, etc.
                alcoholString += String.format("-%sol", substituentsPrefixes[functionalGroupCarbonLocations.get(4).size()]);
            }
            //Processing for alcohol as a substituent group
            else {
                if (reversenumbers) {
                    for (int i = functionalGroupCarbonLocations.get(4).size() - 1; i >= 0; i--) {
                        if (i == functionalGroupCarbonLocations.get(4).size() - 1) alcoholString += String.format("%s", parentChainLength - functionalGroupCarbonLocations.get(4).get(i));
                        else alcoholString += String.format(",%s", parentChainLength - functionalGroupCarbonLocations.get(4).get(i));
                    }
                }
                else {
                    for (int i = 0; i < functionalGroupCarbonLocations.get(4).size(); i++) {
                        if (i == 0) alcoholString += String.format("%s", functionalGroupCarbonLocations.get(4).get(i) + 1);
                        else alcoholString += String.format(",%s", functionalGroupCarbonLocations.get(4).get(i) + 1);
                    }
                }
                alcoholString += String.format("-%shydroxy-", substituentsPrefixes[functionalGroupCarbonLocations.get(4).size()]);
            }
        }
    }
    public static void processAmine() {
        /* Processing for amine */
        if (functionalGroupsPresent[5]) {
            //Processing for when amine is main functional group
            if (priorityGroup == 5) {
                //Add name depending on parent chain --> Example: methane-, ethane-, etc.
                //If no. of amines == 1 --> name becomes methan-, ethan-, etc.
                //if (functionalGroupCarbonLocations.get(5).size()==1) amineString += String.format("%san-", chainPrefixes[parentChainLength]);
                //else amineString += String.format("%sane-", chainPrefixes[parentChainLength]);
                //Add locations of amine groups --> Example: 1,2,2-  3,4-   etc.
                if (reversenumbers) {
                    for (int i = functionalGroupCarbonLocations.get(5).size() - 1; i >= 0; i--) {
                        if (i == functionalGroupCarbonLocations.get(5).size() - 1) amineString += String.format("%s", parentChainLength - functionalGroupCarbonLocations.get(5).get(i));
                        else amineString += String.format(",%s", parentChainLength - functionalGroupCarbonLocations.get(5).get(i));
                    }
                }
                else {
                    for (int i = 0; i < functionalGroupCarbonLocations.get(5).size(); i++) {
                        if (i == 0) amineString += String.format("%s", functionalGroupCarbonLocations.get(5).get(i) + 1);
                        else amineString += String.format(",%s", functionalGroupCarbonLocations.get(5).get(i) + 1);
                    }
                }
                //Add name depending on no. of amine groups --> Example: -amine, -diamine, -triamine, etc.
                amineString += String.format("-%samine", substituentsPrefixes[functionalGroupCarbonLocations.get(5).size()]);
            }
            //Processing for amine as a substituent group
            else {
                if (reversenumbers) {
                    for (int i = functionalGroupCarbonLocations.get(5).size() - 1; i >= 0; i--) {
                        if (i == functionalGroupCarbonLocations.get(5).size() - 1) amineString += String.format("%s", parentChainLength - functionalGroupCarbonLocations.get(5).get(i));
                        else amineString += String.format(",%s", parentChainLength - functionalGroupCarbonLocations.get(5).get(i));
                    }
                }
                else {
                    for (int i = 0; i < functionalGroupCarbonLocations.get(5).size(); i++) {
                        if (i == 0) amineString += String.format("%s", functionalGroupCarbonLocations.get(5).get(i) + 1);
                        else amineString += String.format(",%s", functionalGroupCarbonLocations.get(5).get(i) + 1);
                    }
                }
                amineString += String.format("-%samino-", substituentsPrefixes[functionalGroupCarbonLocations.get(5).size()]);
            }
        }
    }

    public static void processAlkane() {
        if (priorityGroup!=8) {
            if (functionalGroupsPresent[6] || functionalGroupsPresent[7]) alkaneString += String.format("%s-", chainPrefixes[parentChainLength]);
            else alkaneString += String.format("%s", chainPrefixes[parentChainLength]);
        }
        else {
            alkaneString += String.format("%sane", chainPrefixes[parentChainLength]);
        }
    }
    public static void processAlkene() {
        if (functionalGroupsPresent[6]) {
            //Processing for alkene as priority group
            if (priorityGroup == 6) {
                if (reversenumbers) {
                    for (int i = functionalGroupCarbonLocations.get(6).size() - 1; i >= 0; i--) {
                        if (i == functionalGroupCarbonLocations.get(6).size() - 1)
                            alkeneString += String.format("%s", parentChainLength - functionalGroupCarbonLocations.get(6).get(i));
                        else
                            alkeneString += String.format(",%s", parentChainLength - functionalGroupCarbonLocations.get(6).get(i));
                    }
                } else {
                    for (int i = 0; i < functionalGroupCarbonLocations.get(6).size(); i++) {
                        if (i == 0)
                            alkeneString += String.format("%s", functionalGroupCarbonLocations.get(6).get(i) + 1);
                        else alkeneString += String.format(",%s", functionalGroupCarbonLocations.get(6).get(i) + 1);
                    }
                }
                if (functionalGroupsPresent[7]) {
                    alkeneString += String.format("-%sen-", substituentsPrefixes[functionalGroupCarbonLocations.get(6).size()]);
                } else {
                    alkeneString += String.format("-%sene", substituentsPrefixes[functionalGroupCarbonLocations.get(6).size()]);
                }
            }
            //Processing for when alkene is substituent grp
            else {
                if (reversenumbers) {
                    for (int i = functionalGroupCarbonLocations.get(6).size() - 1; i >= 0; i--) {
                        if (i == functionalGroupCarbonLocations.get(6).size() - 1)
                            alkeneString += String.format("%s", parentChainLength - functionalGroupCarbonLocations.get(6).get(i));
                        else
                            alkeneString += String.format(",%s", parentChainLength - functionalGroupCarbonLocations.get(6).get(i));
                    }
                } else {
                    for (int i = 0; i < functionalGroupCarbonLocations.get(6).size(); i++) {
                        if (i == 0)
                            alkeneString += String.format("%s", functionalGroupCarbonLocations.get(6).get(i) + 1);
                        else alkeneString += String.format(",%s", functionalGroupCarbonLocations.get(6).get(i) + 1);
                    }
                }
                if (priorityGroup == 4 && functionalGroupsPresent[7]) alkeneString += String.format("-%sen-", substituentsPrefixes[functionalGroupCarbonLocations.get(6).size()]);
                else if (priorityGroup == 4 && !functionalGroupsPresent[7]) alkeneString += String.format("-%sene-", substituentsPrefixes[functionalGroupCarbonLocations.get(6).size()]);
                else alkeneString += String.format("-%sen", substituentsPrefixes[functionalGroupCarbonLocations.get(6).size()]);
            }
        }
    }
    public static void processAlkyne() {
        if (functionalGroupsPresent[7]) {
            //Processing for when alkyne is main functional grp
            if (priorityGroup == 7) {
                if (reversenumbers) {
                    for (int i = functionalGroupCarbonLocations.get(7).size() - 1; i >= 0; i--) {
                        if (i == functionalGroupCarbonLocations.get(7).size() - 1)
                            alkyneString += String.format("%s", parentChainLength - functionalGroupCarbonLocations.get(7).get(i));
                        else
                            alkyneString += String.format(",%s", parentChainLength - functionalGroupCarbonLocations.get(7).get(i));
                    }
                } else {
                    for (int i = 0; i < functionalGroupCarbonLocations.get(7).size(); i++) {
                        if (i == 0)
                            alkyneString += String.format("%s", functionalGroupCarbonLocations.get(7).get(i) + 1);
                        else alkyneString += String.format(",%s", functionalGroupCarbonLocations.get(7).get(i) + 1);
                    }
                }
                alkyneString += String.format("-%syne", substituentsPrefixes[functionalGroupCarbonLocations.get(7).size()]);
            }
            //Processing for when alkyne is substituent grp
            else {
                if (reversenumbers) {
                    for (int i = functionalGroupCarbonLocations.get(7).size() - 1; i >= 0; i--) {
                        if (i == functionalGroupCarbonLocations.get(7).size() - 1)
                            alkyneString += String.format("%s", parentChainLength - functionalGroupCarbonLocations.get(7).get(i));
                        else
                            alkyneString += String.format(",%s", parentChainLength - functionalGroupCarbonLocations.get(7).get(i));
                    }
                } else {
                    for (int i = 0; i < functionalGroupCarbonLocations.get(6).size(); i++) {
                        if (i == 0)
                            alkyneString += String.format("%s", functionalGroupCarbonLocations.get(7).get(i) + 1);
                        else alkyneString += String.format(",%s", functionalGroupCarbonLocations.get(7).get(i) + 1);
                    }
                }
                if (priorityGroup == 4)
                    alkyneString += String.format("-%syne-", substituentsPrefixes[functionalGroupCarbonLocations.get(7).size()]);
                else if (priorityGroup == 6)
                    alkyneString += String.format("-%syne", substituentsPrefixes[functionalGroupCarbonLocations.get(7).size()]);
                else
                    alkyneString += String.format("-%s", substituentsPrefixes[functionalGroupCarbonLocations.get(7).size()]);
            }
        }
    }

    public static void resetAlgorithm() {
        parentChainLength = 0;
        carbons = new ArrayList<>(); compoundAtoms = new ArrayList<>(); parentChainCarbons = new ArrayList<>();
        functionalGroupsPresent = new boolean[]{false,false,false,false,false,false,false,false,false};
        functionalGroupCarbonLocations = new ArrayList<ArrayList<Integer>>();
        bromineLocations = new ArrayList<>(); chlorineLocations=new ArrayList<>(); fluorineLocations=new ArrayList<>();  iodineLocations=new ArrayList<>();
        visited = new ArrayList<>(); alkenesVisited = new ArrayList<>(); alkynesVisited = new ArrayList<>();
        reversenumbers = false;
        priorityGroup=1000;
        functionalGroupString=""; carboxylicString=""; amideString=""; alcoholString=""; amineString=""; alkeneString=""; alkyneString=""; alkaneString="";
    }







}
