// Skeleton implementation written by Joe Zachary for CS 3500, September 2013.
// Version 1.1 (Fixed error in comment for RemoveDependency.)
// Version 1.2 - Daniel Kopta 
//               (Clarified meaning of dependent and dependee.)
//               (Clarified names in solution/project structure.)
//Version 1.3 - Haley Feten, CS 3500

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpreadsheetUtilities
{

    /// <summary>
    /// (s1,t1) is an ordered pair of strings
    /// t1 depends on s1; s1 must be evaluated before t1
    /// 
    /// A DependencyGraph can be modeled as a set of ordered pairs of strings.  Two ordered pairs
    /// (s1,t1) and (s2,t2) are considered equal if and only if s1 equals s2 and t1 equals t2.
    /// Recall that sets never contain duplicates.  If an attempt is made to add an element to a 
    /// set, and the element is already in the set, the set remains unchanged.
    /// 
    /// Given a DependencyGraph DG:
    /// 
    ///    (1) If s is a string, the set of all strings t such that (s,t) is in DG is called dependents(s).
    ///        (The set of things that depend on s)    
    ///        
    ///    (2) If s is a string, the set of all strings t such that (t,s) is in DG is called dependees(s).
    ///        (The set of things that s depends on) 
    //
    // For example, suppose DG = {("a", "b"), ("a", "c"), ("b", "d"), ("d", "d")}
    //     dependents("a") = {"b", "c"}
    //     dependents("b") = {"d"}
    //     dependents("c") = {}
    //     dependents("d") = {"d"}
    //     dependees("a") = {}
    //     dependees("b") = {"a"}
    //     dependees("c") = {"a"}
    //     dependees("d") = {"b", "d"}
    /// </summary>
    public class DependencyGraph
    {
        private Dictionary<string, HashSet<string>> dependents;
        private Dictionary<string, HashSet<string>> dependees;
        private int size;

        /// <summary>
        /// Creates an empty DependencyGraph.
        /// </summary>
        public DependencyGraph()
        {
            dependents = new Dictionary<string, HashSet<string>>();
            dependees = new Dictionary<string, HashSet<string>>();
            size = 0;
        }


        /// <summary>
        /// The number of ordered pairs in the DependencyGraph.
        /// </summary>
        public int Size
        {
            get
            {
                return size;
            }
        }


        /// <summary>
        /// The size of dependees(s).
        /// This property is an example of an indexer.  If dg is a DependencyGraph, you would
        /// invoke it like this:
        /// dg["a"]
        /// It should return the size of dependees("a")
        /// </summary>
        public int this[string s]
        {
            get
            {
                if (dependees.TryGetValue(s, out HashSet<string> dependeeList))
                {
                    return dependeeList.Count;
                }
                return 0;
            }
        }


        /// <summary>
        /// Reports whether dependents(s) is non-empty.
        /// </summary>
        public bool HasDependents(string s)
        {
            if (dependents.TryGetValue(s, out HashSet<string> allDependents))
            {
                if (allDependents.Count > 0)
                {
                    return true;
                }
            }
            return false;
        }


        /// <summary>
        /// Reports whether dependees(s) is non-empty.
        /// </summary>
        public bool HasDependees(string s)
        {
            if (dependees.TryGetValue(s, out HashSet<string> allDependees))
            {
                if (allDependees.Count > 0)
                {
                    return true;
                }
            }
            return false;
        }


        /// <summary>
        /// Enumerates dependents(s).
        /// </summary>
        public IEnumerable<string> GetDependents(string s)
        {
            HashSet<string> output = new HashSet<string>();
            if (dependents.TryGetValue(s, out HashSet<string> value))
            {
                return value.AsEnumerable<string>();
            }
            return output.AsEnumerable<string>();
        }

        /// <summary>
        /// Enumerates dependees(s).
        /// </summary>
        public IEnumerable<string> GetDependees(string s)
        {
            HashSet<string> output = new HashSet<string>();
            if (dependees.TryGetValue(s, out HashSet<string> value))
            {
                return value.AsEnumerable<string>();
            }
            return output.AsEnumerable<string>();
        }


        /// <summary>
        /// <para>Adds the ordered pair (s,t), if it doesn't exist</para>
        /// 
        /// <para>This should be thought of as:</para>   
        /// 
        ///   t depends on s
        ///
        /// </summary>
        /// <param name="s"> s must be evaluated first. T depends on S</param>
        /// <param name="t"> t cannot be evaluated until s is</param>        /// 
        public void AddDependency(string s, string t)
        {
            // If item not in the dependency graph, add it to list of dependees and dependents
            AddToDependencyGraph(s, t);
            if (AddRelationship(s, t, dependents))
            {
                AddRelationship(t, s, dependees);
                size++;
            }

        }

        /// <summary>
        /// Adds s and t to the dependency graph and into dependents and dependees.
        /// </summary>
        /// <param name="s"></param>
        /// <param name="t"></param>
        private void AddToDependencyGraph(string s, string t)
        {
            if (!dependents.ContainsKey(s))
            {
                dependents.Add(s, new HashSet<string>());
                dependees.Add(s, new HashSet<string>());
            }
            if (!dependents.ContainsKey(t))
            {
                dependents.Add(t, new HashSet<string>());
                dependees.Add(t, new HashSet<string>());
            }

        }

        /// <summary>
        /// Inputs the relationship into either dependents or dependees.
        /// </summary>
        /// <param name="keyValue"></param>
        /// <param name="hashSetValue"></param>
        /// <param name="hashMap"></param>
        private bool AddRelationship(string keyValue, string hashSetValue, Dictionary<string, HashSet<string>> hashMap)
        {

            // Check first to see if the dependency exists
            if (hashMap.TryGetValue(keyValue, out HashSet<string> set))
            {
                if (!set.Contains(hashSetValue))
                {
                    set.Add(hashSetValue);
                    return true;
                }
                // If the value is not in the set, then add to the set

            }
            return false;
        }

        /// <summary>
        /// Removes the ordered pair (s,t), if it exists
        /// </summary>
        /// <param name="s"></param>
        /// <param name="t"></param>
        public void RemoveDependency(string s, string t)
        {
            if (RemoveRelationship(s, t, dependents))
            {
                RemoveRelationship(t, s, dependees);
                size--;
                // If there is no longer a reference to either s or t, then remove the value entirely
                if (!HasDependees(s) && !HasDependents(s))
                {
                    RemoveFromDependencyGraph(s);
                }

                if (!HasDependees(t) && !HasDependents(t))
                {
                    RemoveFromDependencyGraph(t);
                }
            }

           

        }

        /// <summary>
        /// Removes value from dependency graph.
        /// </summary>
        /// <param name="s"></param>
        private void RemoveFromDependencyGraph(string key)
        {
            dependees.Remove(key);
            dependents.Remove(key);
        }

        /// <summary>
        /// Removes relationship from the specified dependency/dependent graph.
        /// </summary>
        /// <param name="key"></param>
        /// <param name="valueInSet"></param>
        /// <param name="relationshipSet"></param>
        /// <returns></returns>
        private bool RemoveRelationship(string key, string valueInSet, Dictionary<string, HashSet<string>> relationshipSet)
        {
            if (relationshipSet.TryGetValue(key, out HashSet<string> set))
            {
                if (set.Contains(valueInSet))
                {
                    set.Remove(valueInSet);
                    return true;
                }
            }
            return false;
        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (s,r).  Then, for each
        /// t in newDependents, adds the ordered pair (s,t).
        /// </summary>
        public void ReplaceDependents(string s, IEnumerable<string> newDependents)
        {
            if (dependents.TryGetValue(s, out HashSet<string> valueSet))
            {
                HashSet<string> hashSetToBeDeleted = new HashSet<string>(valueSet);
                foreach (string stringToBeDeleted in hashSetToBeDeleted)
                {
                    RemoveDependency(s, stringToBeDeleted);
                }
            }
            foreach (string stringToBeAdded in newDependents)
            {
                AddDependency(s, stringToBeAdded);
            }

        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (r,s).  Then, for each 
        /// t in newDependees, adds the ordered pair (t,s).
        /// </summary>
        public void ReplaceDependees(string s, IEnumerable<string> newDependees)
        {
            if (dependees.TryGetValue(s, out HashSet<string> valueSet))
            {
                HashSet<string> hashSetToBeDeleted = new HashSet<string>(valueSet);
                foreach (string keyToBeDeleted in hashSetToBeDeleted)
                {
                    RemoveDependency(keyToBeDeleted, s);
                }
            }
            foreach (string stringToBeAdded in newDependees)
            {
                AddDependency(stringToBeAdded, s);
            }

        }

    }

}

