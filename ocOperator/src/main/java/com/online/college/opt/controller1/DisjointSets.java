package com.online.college.opt.controller1;

public class DisjointSets {

	private int[] s;

    public DisjointSets(int numElements)

    {

      s = new int[numElements];

        for (int i = 0; i < s.length; i++)

            s[i] = -1;

    }

    public void union(int root1, int root2) throws Exception

    {

        assertIsRoot(root1);

        assertIsRoot(root2);

        if (root1 == root2)

            throw new Exception("Union: root1 == root2 " + root1);



        if (s[root2] < s[root1])

            s[root1] = root2;

        else

        {

            if (s[root1] == s[root2])

                s[root1]--;

            s[root2] = root1;

        }

    }

    public int find(int x) throws Exception

    {

        assertIsItem(x);

        if (s[x] < 0)

            return x;

        else

            return s[x] = find(s[x]);

    }

    private void assertIsRoot(int root) throws Exception

    {

        assertIsItem(root);

        if (s[root] >= 0)

            throw new Exception("Union: " + root + " not a root");

    }



    private void assertIsItem(int x) throws Exception

    {

        if (x < 0 || x >= s.length)

            throw new Exception("Disjoint sets: " + x + " not an item");

    } 

}
