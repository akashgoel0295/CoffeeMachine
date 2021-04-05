package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Machine {
    public Outlets outlets;
    public Map<String,Integer> total_items_quantity;
    public Map<String,Map<String,Integer>> beverages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Outlets{
        public int count_n;
    }
}


