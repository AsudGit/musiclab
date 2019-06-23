package com.lhs.musiclab.utils;

import com.lhs.musiclab.pojo.BlogItem;
import com.lhs.musiclab.pojo.Tag;

import java.util.LinkedList;
import java.util.List;

public class QuickSort {
    private static boolean DESC = false;
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

    /***
     * 降序
     */
    public static void enableDESC(){
        DESC = true;
    }

    public static void sort(int[] ints,int head,int tail){
        int i=head,j=tail,pivot = ints[i],t=i;
        if(ints.length>0) {
            while (i < j) {
                if (t == j) {
                    i++;
                    while (i < j && ((pivot > ints[i])^DESC)) {
                        i++;
                    }
                    ints[j] = ints[i];
                    t = i;
                } else {
                    while (i < j && ((pivot < ints[j])^DESC)) {
                        j--;
                    }
                    ints[i] = ints[j];
                    t = j;
                }
            }
            ints[t] = pivot;
            if(t+1<tail){
                sort(ints,t+1,tail);
            }
            if(t-1>head){
                sort(ints,head,t-1);
            }

        }
    }
    public static void sort(List<Tag> list, int head, int tail){
        int i=head,j=tail,t=i;
        Tag pivot = list.get(i);
        if(list.size()>0){
            while (i < j) {
                if (t == j) {
                    i++;
                    while (i < j && ((pivot.compareTo(list.get(i))>0)^DESC)) {
                        i++;
                    }
                    list.set(j,list.get(i));
                    t = i;
                } else {
                    while (i < j && ((pivot.compareTo(list.get(j))<0)^DESC)) {
                        j--;
                    }
                    list.set(i,list.get(j));
                    t = j;
                }
            }
            list.set(t,pivot);
            if(t+1<tail){
                sort(list,t+1,tail);
            }
            if(t-1>head){
                sort(list,head,t-1);
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
                        while (i < j && ((pivot.compareTo(linkedList.get(i))>0)^DESC)) {
                            i++;
                        }
                        linkedList.set(j,linkedList.get(i));
                        t = i;
                    } else {
                        while (i < j && ((pivot.compareTo(linkedList.get(j))<0)^DESC)) {
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
