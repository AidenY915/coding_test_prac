const readline = require('readline');
const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

const input = [];
let lineCnt = 0;
rl.on('line', (line) => {
  lineCnt++;
  input.push(line);
  if (lineCnt == 2) {
    rl.close();
  }
});

rl.on('close', () => {
  const [n, m] = input[0].split(' ').map(Number);
  const abilities = input[1].split(' ').map(Number);

  abilities.sort((n1, n2) => {
    return Number(n1) - Number(n2); // -는 문자열 안됨 그래서 자동 숫자 계산, sort는 기본값이 문자열로 변경해서 비교함.
  });

  let teamCnt = 0;
  let i = 0;
  let j = n - 1;
  while (i < j) {
    if (m <= abilities[i] + abilities[j]) {
      i++;
      j--;
      teamCnt++;
      continue;
    }
    i++;
  }
  console.log(teamCnt);
});
