package Base.Interfaces;

import Abstracts.GameObject;

public interface IGameObjectBuilderEmpty<T extends GameObject> {
    T buildEmpty();
    T resetValues(T gameObject);
}
