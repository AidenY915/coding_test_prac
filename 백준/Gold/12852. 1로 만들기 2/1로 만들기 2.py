#1시간 미만
import sys
import collections # 승엽아 이거 맞니? 제일 빠른 거


def bfsForDp(arr ,n) :
    que = collections.deque()
    que.append(1)
    while(len(que)>0): #deque.count()는 
        num = que.popleft()
        if(num*3 <= n and arr[num*3] == 0):
            arr[num*3] = num
            que.append(num*3)
        if(num*2 <= n and arr[num*2] == 0):
            arr[num*2] = num
            que.append(num*2)
        if(num+1 <= n and arr[num+1] == 0):
            arr[num+1] = num
            que.append(num+1)

        if(num*3 == n):
            return arr[num*3]
        if(num*2 == n):
            return arr[num*2]
        if(num+1 == n):
            return arr[num+1]            
    
    
def ascendLog(arr, n):
    idx = n
    log = []
    while(idx > 0):
        log.append(idx)
        idx = arr[idx]
    return log #전부 동적할당 힙에 저장되기 때문에 리턴됨.

n = int(sys.stdin.readline())
arr = [0 for _ in range(n+1)]
bfsForDp(arr, n)
log = ascendLog(arr, n)
print(str(len(log)-1) + "\n" + " ".join(map(str, log)))







# 분석
# 그리디하게 풀 수 없음.
# 10 9 3 1
# 10 5 4 2 1
# 어떻게 해야할까
# 결국 다 해봐야 함. 최악 n^3

# 하다 보면 부분 중복 발생 가능
# 10 9 8 4 2 1
# 4 2 1은 중복 발생 이게 많아지면 계산량 많아짐.

# 중복만 제거해도 3n안에 실행가능
# DP로 중복 회피
# 1부터 위로 역연산으로 올라가면서 최소 횟수 갱신

# 1*3 1*2 1+1 해가면서 bfs로 도달. 모든 것이 다 찰 때까지.

# 설계
# n+1크기의 배열 할당
# bfs로 1부터 올라가면서, 각 배열에는 이전 값을 저장, 만약 차 있으면 넘어감.
#그러다가 n을 만나면 스탑.



#배운점 deque는 내부적으로 더블 링크드 리스트 -> 링크드 리스트 쓸 때 쓰면 유용
#근데 어떻게 len을 쓸 수 있는가? __len__ 내장 함수를 호출하기 때문
#애초에 len(객체)는 정의되어 있는게 아니고, 내장 함수를 객체의 내장 함수를 call하는 형태.
#len(o): 
#   return o.__len__
#이런 식인 것