package com.process;

import com.entity.Artist;
import com.entity.Song;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.pipes.PipeFunction;
import org.codehaus.groovy.runtime.IteratorClosureAdapter;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by MonsterX on 16.10.2016.
 */
public class TinkerGraph {

    public static Predicate<Vertex> typeIsSong() {
        return t -> t.getProperty("type").toString().equals("song");
    }
    public static Predicate<Vertex> skipFirstLine() {
        return t -> !t.getId().equals(0);
    }

    public static Iterable<Vertex> filterVertex (List<Vertex> vertices, Predicate<Vertex> predicate) {
        return vertices.stream().filter( predicate ).collect(Collectors.<Vertex>toList());
    }

    @Test
    public void testIteratingGraph() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        System.out.println("Vertices of " + graph);
        Vertex a = graph.getVertex("1");
        System.out.println("vertex " + a.getId() + " has name " + a.getProperty("name"));
    }

    @Test
    public void songWrittenByAuthorsJava8() throws IOException {
        final Graph graph = new com.tinkerpop.blueprints.impls.tg.TinkerGraph();
        GraphMLReader reader = new GraphMLReader(graph);
        InputStream is = null;
        is = new BufferedInputStream(new FileInputStream("D:\\greatful_dead_analysis.xml"));
        reader.inputGraph(is);
        List<Vertex> s = (List<Vertex>) graph.getVertices();
        List<Vertex> s1 = (List<Vertex>) filterVertex(s, skipFirstLine());
        System.out.println(s1.get(0));
    }

    @Test
    public void songWrittenByAuthors() {
        final Graph graph = new com.tinkerpop.blueprints.impls.tg.TinkerGraph();
        GraphMLReader reader = new GraphMLReader(graph);
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream("D:\\greatful_dead_analysis.xml"));
            reader.inputGraph(is);
            final GremlinPipeline pipe = new GremlinPipeline();
            Iterator iter = graph.getVertices().iterator();

            int count = 1;
            Map<Song,Artist> songAndAuthors = new HashMap<Song, Artist>();
            while(iter.hasNext()) {
                Vertex vertex = (Vertex) iter.next();
                if(count != 1) { //skip first
                    System.out.println(typeIsSong());
                    if (vertex.getProperty("type").equals("song")) {
                        Song song = new Song();
                        song.setName(vertex.getProperty("name"));
                        song.setType(vertex.getProperty("type"));
                        song.setPerformances(vertex.getProperty("performances"));
                        song.setSong_type(vertex.getProperty("song_type"));
                        for(Edge edge : vertex.getEdges(Direction.OUT)) {
                            if(edge.getLabel().equals("written_by")) {
                                Artist artist = new Artist();
                                artist.setName(edge.getVertex(Direction.IN).getProperty("name").toString());
                                artist.setType(edge.getVertex(Direction.IN).getProperty("type").toString());
                                song.setWritten_by(artist);
                                songAndAuthors.put(song, artist);
                            }
                        }

                    }
                }
                count++;
            }
            songAndAuthors.entrySet().forEach((x) -> System.out.println(x.getKey().getName() + " " + x.getValue().getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    *   RAMBLE ON ROSE Hunter
        ESTIMATED PROPHET Barlow
        ALABAMA BOUND Traditional
        KANSAS CITY Leiber_Stoller
        THE ELEVEN Hunter
        YOU DONT LOVE ME Willie_Cobb
        BALLAD OF CASEY JONES Traditional
        LIBERTY Hunter
        COWBOY SONG Unknown
        COSMIC CHARLEY Hunter
        SIMPLE TWIST OF FATE Bob_Dylan
        RIOT IN CELL BLOCK Lieber_Stoller
        VIOLA LEE BLUES Noah_Lewis
        ODE FOR BILLIE DEAN Jorma_Kaukonen
    *
    * */


    @Test
    public void testPipeGremlin() {
        final Graph graph = new com.tinkerpop.blueprints.impls.tg.TinkerGraph();
        GraphMLReader reader = new GraphMLReader(graph);
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream("D:\\greatful_dead_analysis.xml"));
            reader.inputGraph(is);
            final GremlinPipeline pipe = new GremlinPipeline();
            List list = pipe.start(graph.getEdge(1).getVertex(Direction.IN).getProperty("name")
                    + " " + (graph.getEdge(1).getLabel()
                    + "-->"
                    + (graph.getEdge(1).getVertex(Direction.OUT).getProperty("name"))))
                    .toList();
            System.out.println(list);

          String list1 =  pipe.start(graph.getEdge(1).getVertex(Direction.IN))
                    .out("followed_by")
                    .property("name")
                    .filter(new PipeFunction<String, Boolean>() {
                        public Boolean compute(String o) {
                            return o.contains("B");
                        }
                    }).next(1).toString();
            System.out.println(list1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        * [NOT FADE AWAY followed_by-->HEY BO DIDDLEY]
          [BLACKBIRD]
        *
        * */
    }

    @Test
    public void testReadGraphFromFile() {
        Graph graph = new com.tinkerpop.blueprints.impls.tg.TinkerGraph();
        GraphMLReader reader = new GraphMLReader(graph);
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream("D:\\greatful_dead_analysis.xml"));
            reader.inputGraph(is);
            Iterable<Vertex> verticles = graph.getVertices();
            Iterable<Edge> edges = graph.getEdges();
            Map<Artist, Song> band = new HashMap<Artist, Song>();
            int count = 0;
            for (Vertex vertex : verticles) {
                for (Edge edge : edges) {
                    Artist artist = new Artist();
                    Song song = new Song();
                    count++;
                    if (vertex.getPropertyKeys().size() == 2) {
                        artist.setName(vertex.getProperty("name").toString());
                        artist.setType(vertex.getProperty("type").toString());
                    } else if (vertex.getPropertyKeys().size() == 4) {
                        song.setName(vertex.getProperty("name").toString());
                        song.setPerformances(Integer.parseInt(vertex.getProperty("performances").toString()));
                        song.setSong_type(vertex.getProperty("song_type").toString());
//                        song.setFollowed_by(edge.getProperty("followed_by").toString());
                        song.setType(vertex.getProperty("type").toString());
                    }
                    count++;
                    band.put(artist, song);
                }
            }
            System.out.println(band);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
