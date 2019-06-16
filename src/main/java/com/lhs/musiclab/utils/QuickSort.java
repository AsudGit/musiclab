package com.lhs.musiclab.utils;

import com.lhs.musiclab.pojo.BlogItem;

import java.util.LinkedList;

public class QuickSort {
    public static void main(String[] args) {
/*        int[] a = {3, 5, 2, 1, 67, 4, 57, 2, 45};
        for (int i : a) {
            System.out.print(i+",");
        }
        intsSort(a,0,a.length-1);
        System.out.println();
        for (int i : a) {
            System.out.print(i+",");
        }*/

    }

    public static void intsSort(int[] ints,int head,int tail){
        int i=head,j=tail,pivot = ints[i],t=i;
        if(ints.length>0) {
            while (i < j) {
                if (t == j) {
                    i++;
                    while (i < j && pivot > ints[i]) {
                        i++;
                    }
                    ints[j] = ints[i];
                    t = i;
                } else {
                    while (i < j && pivot < ints[j]) {
                        j--;
                    }
                    ints[i] = ints[j];
                    t = j;
                }
            }
            ints[t] = pivot;
            if(t+1<tail){
                intsSort(ints,t+1,tail);
            }
            if(t-1>head){
                intsSort(ints,head,t-1);
            }

        }
    }

    public static void linkedlistSort(LinkedList<BlogItem> linkedList, int head, int tail){
        int i=head,j=tail,t=i;
        BlogItem pivot = linkedList.get(i);
        if(linkedList.size()>0){
                while (i < j) {
                    if (t == j) {
                        i++;
                        while (i < j && pivot.compareTo(linkedList.get(i))>0) {
                            i++;
                        }
                        linkedList.set(j,linkedList.get(i));
                        t = i;
                    } else {
                        while (i < j && pivot.compareTo(linkedList.get(j))<0) {
                            j--;
                        }
                        linkedList.set(i, linkedList.get(j));
                        t = j;
                    }
                }
                linkedList.set(t, pivot);
                if(t+1<tail){
                    linkedlistSort(linkedList,t+1,tail);
                }
                if(t-1>head){
                    linkedlistSort(linkedList,head,t-1);
                }

            }
    }
}
