package com.backend.filb;

import com.backend.filb.infra.EmotionApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FilbApplicationTests {
    @Autowired
    private final EmotionApi emotionApi;

   @Autowired
    FilbApplicationTests(ChatgptService chatgptService, EmotionApi emotionApi) {
       this.emotionApi = emotionApi;
    }

    @Test
    void test1() throws JsonProcessingException {
       emotionApi.getReport("오늘 하루는 감정의 롤러코스터였다. 아침에 눈을 뜨자마자 창밖에 비가 내리고 있는 것을 보고 기분이 가라앉았다. 비가 오면 마음까지 촉촉해지는 것 같아, 왠지 모르게 우울함이 밀려온다. 아침 커피를 마시면서 창밖을 바라보는데, 빗소리가 너무 좋아서 한동안 멍하니 앉아있었다. 빗소리는 나를 차분하게 해주기도 하지만, 동시에 마음 깊은 곳에 묻어둔 감정들을 끄집어내는 것 같다.회사에 도착했을 때는 기분이 조금 나아졌지만, 오전 내내 일에 집중하기가 힘들었다. 새로운 프로젝트가 시작되었는데, 기대와 두려움이 교차했다. 팀원들과의 회의에서 내가 맡은 부분에 대해 설명할 때, 모든 시선이 나에게 집중되는 순간 심장이 두근거렸다. 다들 열심히 들어주는 모습에 안도하면서도, 내가 잘 해낼 수 있을지 걱정이 앞섰다.점심시간에는 동료들과 함께 근처 카페에 갔다. 그곳의 따뜻한 분위기와 맛있는 음식 덕분에 기분이 많이 풀렸다. 특히, 동료들과의 대화가 오늘의 하이라이트였다. 서로의 고민을 털어놓고 웃고 떠들다 보니 마음이 한결 가벼워졌다. 사람들과의 소통이 이렇게 큰 힘이 될 줄이야.오후에는 프로젝트 작업에 몰두했다. 시간이 어떻게 흘러갔는지 모를 정도로 집중했다. 하지만 퇴근 시간이 다가오면서 다시 피로감이 몰려왔다. 집에 돌아오는 길에는 오늘 하루를 되돌아보며 여러 가지 생각이 스쳐 지나갔다. 내가 정말 잘하고 있는 걸까? 앞으로의 일들은 어떻게 될까? 하지만 동시에, 하루를 무사히 마쳤다는 안도감도 들었다.집에 돌아와 저녁을 먹고 나서야 비로소 오늘 하루가 끝났다는 실감이 났다. 샤워를 하고 따뜻한 차 한 잔을 마시며, 나 자신에게 오늘도 수고했다고 말해주고 싶다.");
    }


}
