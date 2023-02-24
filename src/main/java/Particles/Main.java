package Particles;

import Particles.System.ParticleSystem;

public class Main {

    public static void main(String[] args) {
        var system = new ParticleSystem();
        system.init();
        system.start();
    }

}
