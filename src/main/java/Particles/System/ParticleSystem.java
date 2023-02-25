package Particles.System;

import ADT.Vector;
import ADT.Vector3D;
import Particles.Graphics.Renderer;
import Particles.Graphics.Renderer3D;
import org.tinspin.index.kdtree.KDTree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static Particles.Graphics.Renderer3D.UNIT_SIZE;
import static Particles.Settings.NUM_PARTICLES;

public class ParticleSystem implements System {
    private final Renderer renderer = new Renderer3D();
    private final List<Particle> particles = new ArrayList<>();
    private final KDTree<Particle> treeMap = KDTree.create(3);


    @Override
    public Renderer renderer() {
        return this.renderer;
    }

    @Override
    public void start() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::loop, 2000,25, TimeUnit.MILLISECONDS);
    }

    private void loop() {
        update();
        renderer.mouseUpdate();
        renderer.render(particles);
        renderer.mouseReset();
    }

    @Override
    public void update() {
        solver.step(this.particles);
    }

    @Override
    public void init() {
        renderer.init();

        particles.add(new Particle.Massive(Vector.zero3D(), Vector.zero3D()));
        // particles.add(new Particle.Massive(new Vector3D(0, UNIT_SIZE * .1, UNIT_SIZE * .1), Vector.zero3D()));
        // particles.add(new Particle.Massive(new Vector3D(0, UNIT_SIZE *-.1, UNIT_SIZE *-.1), Vector.zero3D()));

        IntStream.range(0,NUM_PARTICLES).forEach(i -> particles.add(new Particle()));
    }
}
