package class13;

import java.util.Arrays;
import java.util.Comparator;

public class Code02_MeetingRoom {
	/**
	 * 会议室怎么安排，场次最多？
	 * 
	 * 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲，给你每一个项目开始的时间和结束的时间
	 * 你来安排宣讲的日程，要求会议室进行的宣讲的场次最多，返回最多的宣讲场次.
	 * 
	 * 贪心策略:
	 * 每次安排结束时间最早的。
	 */
	public static class Meeting {
		int start;
		int end;
		public Meeting(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
	public static int meetingRoom(Meeting meetings[]) {
		if(meetings == null || meetings.length == 0) {
			return 0;
		}
		Arrays.sort(meetings, new meetingComparator());
		int ans = 0;
		int timeLine = 0;
		for(Meeting meeting : meetings ) {
			if(timeLine <= meeting.start) { // 如果当前来到的时间，小于等于下一个会议的开始时间，说明可以安排
				timeLine = meeting.end;
				ans++;
			}
		}
		return ans;
	}
	public static class meetingComparator implements Comparator<Meeting> {
		@Override
		public int compare(Meeting o1, Meeting o2) {
			return o1.end - o2.end;
		}
	}
}
