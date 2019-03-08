using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

using MySql.Data.MySqlClient;


namespace HttpJsonRequest
{
    class Program
    {
        static void Main(string[] args)
        {
            int countRecipes = 0;

            while (countRecipes < 200)
            {
                var client = new WebClient();

                var text = client.DownloadString("https://www.themealdb.com/api/json/v1/1/random.php");

                Recipes recipe = JsonConvert.DeserializeObject<Recipes>(text);

                Console.WriteLine("Count of recipes : " + countRecipes);     
                
                String str = @"server=localhost;database=recipes2;userid=root;password=;";
                MySqlConnection con = null;
                MySqlDataReader reader = null;

                // **Replace**
                if(recipe.meals[0].strInstructions.Contains("\""))
                    recipe.meals[0].strInstructions.Replace("\"", "'");

                List<string> m = new List<string>();
                if(recipe.meals[0].strMeasure1!="")
                    m.Add(recipe.meals[0].strMeasure1);
                if (recipe.meals[0].strMeasure2 != "")
                    m.Add(recipe.meals[0].strMeasure2);
                if (recipe.meals[0].strMeasure3 != "")
                    m.Add(recipe.meals[0].strMeasure3);
                if (recipe.meals[0].strMeasure4 != "")
                    m.Add(recipe.meals[0].strMeasure4);
                if (recipe.meals[0].strMeasure5 != "")
                    m.Add(recipe.meals[0].strMeasure5);
                if (recipe.meals[0].strMeasure6 != "")
                    m.Add(recipe.meals[0].strMeasure6);
                if (recipe.meals[0].strMeasure7 != "")
                    m.Add(recipe.meals[0].strMeasure7);
                if (recipe.meals[0].strMeasure8 != "")
                    m.Add(recipe.meals[0].strMeasure8);
                if (recipe.meals[0].strMeasure9 != "")
                    m.Add(recipe.meals[0].strMeasure9);
                if (recipe.meals[0].strMeasure10 != "")
                    m.Add(recipe.meals[0].strMeasure10);
                if (recipe.meals[0].strMeasure11 != "")
                    m.Add(recipe.meals[0].strMeasure11);
                if (recipe.meals[0].strMeasure12 != "")
                    m.Add(recipe.meals[0].strMeasure12);
                if (recipe.meals[0].strMeasure13 != "")
                    m.Add(recipe.meals[0].strMeasure13);
                if (recipe.meals[0].strMeasure14 != "")
                    m.Add(recipe.meals[0].strMeasure14);
                if (recipe.meals[0].strMeasure15 != "")
                    m.Add(recipe.meals[0].strMeasure15);
                if (recipe.meals[0].strMeasure16 != "")
                    m.Add(recipe.meals[0].strMeasure16);
                if (recipe.meals[0].strMeasure17 != "")
                    m.Add(recipe.meals[0].strMeasure17);
                if (recipe.meals[0].strMeasure18 != "")
                    m.Add(recipe.meals[0].strMeasure18);
                if (recipe.meals[0].strMeasure19 != "")
                    m.Add(recipe.meals[0].strMeasure19);
                if (recipe.meals[0].strMeasure20 != "")
                    m.Add(recipe.meals[0].strMeasure20);

                string half = "½";
                string quater = "¼";
                string threeFour = "¾";
                int countMes = m.Count;
                Console.WriteLine("------------------ " + countMes);
                for (int i = 0; i < countMes; i++)
                {
                    if(m[i] == null)
                    {
                        Console.WriteLine("measure was null");
                    }
                    else if (m[i].Contains(half))
                    {
                        if(m[i].IndexOf(half) > 0)
                            m[i] = m[i].Replace(half, " 1/2");
                        else
                            m[i] = m[i].Replace(half, "1/2");
                    }
                    else if (m[i].Contains(quater))
                    {
                        if (m[i].IndexOf(quater) > 0)
                            m[i] = m[i].Replace(quater, " 1/4");
                        else
                            m[i] = m[i].Replace(quater, "1/4");
                    }
                    else if (m[i].Contains(threeFour))
                    {
                        if (m[i].IndexOf(threeFour) > 0)
                            m[i] = m[i].Replace(threeFour, " 3/4");
                        else
                            m[i] = m[i].Replace(threeFour, "3/4");
                    }
                }

                List<string> ing = new List<string>();
                if (recipe.meals[0].strIngredient1 != "" || recipe.meals[0].strIngredient1 != null)
                    ing.Add(recipe.meals[0].strIngredient1);
                if (recipe.meals[0].strIngredient2 != "" || recipe.meals[0].strIngredient2 != null)
                    ing.Add(recipe.meals[0].strIngredient2);
                if (recipe.meals[0].strIngredient3 != "" || recipe.meals[0].strIngredient3 != null)
                    ing.Add(recipe.meals[0].strIngredient3);
                if (recipe.meals[0].strIngredient4 != "" || recipe.meals[0].strIngredient4 != null)
                    ing.Add(recipe.meals[0].strIngredient4);
                if (recipe.meals[0].strIngredient5 != "" || recipe.meals[0].strIngredient5 != null)
                    ing.Add(recipe.meals[0].strIngredient5);
                if (recipe.meals[0].strIngredient6 != "" || recipe.meals[0].strIngredient6 != null)
                    ing.Add(recipe.meals[0].strIngredient6);
                if (recipe.meals[0].strIngredient7 != "" || recipe.meals[0].strIngredient7 != null)
                    ing.Add(recipe.meals[0].strIngredient7);
                if (recipe.meals[0].strIngredient8 != "" || recipe.meals[0].strIngredient8 != null)
                    ing.Add(recipe.meals[0].strIngredient8);
                if (recipe.meals[0].strIngredient9 != "" || recipe.meals[0].strIngredient9 != null)
                    ing.Add(recipe.meals[0].strIngredient9);
                if (recipe.meals[0].strIngredient10 != "" || recipe.meals[0].strIngredient10 != null)
                    ing.Add(recipe.meals[0].strIngredient10);
                if (recipe.meals[0].strIngredient11 != "" || recipe.meals[0].strIngredient11 != null)
                    ing.Add(recipe.meals[0].strIngredient11);
                if (recipe.meals[0].strIngredient12 != "" || recipe.meals[0].strIngredient12 != null)
                    ing.Add(recipe.meals[0].strIngredient12);
                if (recipe.meals[0].strIngredient13 != "" || recipe.meals[0].strIngredient13 != null)
                    ing.Add(recipe.meals[0].strIngredient13);
                if (recipe.meals[0].strIngredient14 != "" || recipe.meals[0].strIngredient14 != null)
                    ing.Add(recipe.meals[0].strIngredient14);
                if (recipe.meals[0].strIngredient15 != "" || recipe.meals[0].strIngredient15 != null)
                    ing.Add(recipe.meals[0].strIngredient15);
                if (recipe.meals[0].strIngredient16 != "" || recipe.meals[0].strIngredient16 != null)
                    ing.Add(recipe.meals[0].strIngredient16);
                if (recipe.meals[0].strIngredient17 != "" || recipe.meals[0].strIngredient17 != null)
                    ing.Add(recipe.meals[0].strIngredient17);
                if (recipe.meals[0].strIngredient18 != "" || recipe.meals[0].strIngredient18 != null)
                    ing.Add(recipe.meals[0].strIngredient18);
                if (recipe.meals[0].strIngredient19 != "" || recipe.meals[0].strIngredient19 != null)
                    ing.Add(recipe.meals[0].strIngredient19);
                if (recipe.meals[0].strIngredient20 != "" || recipe.meals[0].strIngredient20 != null)
                    ing.Add(recipe.meals[0].strIngredient20);

                Console.WriteLine("strMeal = " + recipe.meals[0].strMeal);
                
                string strMeal = recipe.meals[0].strMeal;
                string strCategory = recipe.meals[0].strCategory;
                string strArea = recipe.meals[0].strArea;
                string strInstructions = recipe.meals[0].strInstructions;
                string strImg = recipe.meals[0].strMealThumb;
                string strTags = recipe.meals[0].strTags;
                string strVid = recipe.meals[0].strYoutube;
                int countIng = ing.Count;
                string ingredients = ing[0];
                for (int i = 1; i < countIng; i++)
                {
                    if (ing[i] == null)
                    {
                        Console.Write(" null" + i);
                    }
                    else if (ing[i] == "")
                    {
                        Console.Write(" null" + i);
                    }
                    else
                    {
                        ingredients += ", " + ing[i];
                    }
                }

                string measures= m[0];
                for (int i = 1; i < countMes; i++)
                {
                    if (m[i] != "")
                    {
                        measures += ", " + m[i];
                    }
                }

                string strSrc = recipe.meals[0].strSource;

                int countCopy = 0;

                try
                {


                    con = new MySqlConnection(str);
                    con.Open();                    

                    String cmdTxt = "Select * from meal where Meal like \"" + strMeal + "\" AND Video like \"" + strVid + "\";";
                    MySqlCommand cmdTest = new MySqlCommand(cmdTxt, con);
                    reader = cmdTest.ExecuteReader();

                    while (reader.Read())
                    {
                            countCopy++;
                    }

                        if (countCopy == 0)
                        {
                            reader.Close();

                            String cmdText = "Insert INTO meal (meal, category, area, instructions, image, tags, " +
                            "ingredients, measures, video, source)" +
                            "Values(\"" + strMeal + "\", \"" + strCategory + "\", \"" + strArea + "\", \"" + strInstructions + "\", \""
                            + strImg + "\", \"" + strTags + "\", \"" + ingredients + "\", \"" + measures + "\", \"" + strVid + "\", \"" + strSrc + "\");";

                            MySqlCommand cmd = new MySqlCommand(cmdText, con);
                            reader = cmd.ExecuteReader();

                            while (reader.Read())
                            {
                                Console.WriteLine(reader.GetString(0));
                            }

                            countRecipes++;
                            countCopy = 0;

                        }
                    }
                catch (MySqlException e)
                {
                    Console.WriteLine(e);
                }
                finally
                {
                    if (con != null)
                    {
                        con.Close();
                    }
                }


                
            }

            Console.ReadLine();


        }

        private static int ToInt(string idMeal)
        {
            throw new NotImplementedException();
        }
    }
}
