package patterns;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Solution1 {
	
//	check if a driver can complete all the trips assigned
	public static boolean checkForCompletion(List<TripNode1> trips, int capacity) {
		
//		min heap approach
//		sort the trips with the starting coordinates
		Collections.sort(trips, (x,y)->x.start-y.start);
		
//		min heap with the trips that are going to end in asc order
		PriorityQueue<TripNode1> inProgress = new PriorityQueue<>((x,y)->x.end - y.end);
		
//		iter over the trips
		for(int i=0; i<trips.size(); i++) {
			
			TripNode1 tp = trips.get(i);
			
			
//			check if any inProgress is going to end when we would add a new trip
			while(!inProgress.isEmpty() && inProgress.peek().end <= tp.start) {
				TripNode1 completed = inProgress.poll();
				capacity += completed.capacity;
			}
			
//			add-on the new trip
			if(capacity >= tp.capacity) {
				capacity -= tp.capacity;
				inProgress.add(tp);
			}else {
				return false;
			}
		}
		return true;
	}
	
	public static int maxReward;
	
	
//	maximize the profits for the trips assigned: every pick-trip rewards rs.500
	public static int dfs(List<TripNode1> trips, int capacity, int index, TripNode1 currTrip, List<TripNode1> res) {
		
		if(index >= trips.size()) {
			return 0;
		}
		
//		pick the trip
		int pick = 0;
		if(currTrip == null || currTrip.end <= trips.get(index).start) {
			res.add(trips.get(index));
			pick = 500 + dfs(trips, capacity , index + 1, trips.get(index), res);
			res.remove(res.size() - 1);
		}
		
//		unpick the trip
		int unpick = 0;
		unpick = dfs(trips, capacity, index + 1, currTrip, res);
		
		
		return Math.max(pick, unpick);

	}

}
