package com.example.tacademy.ddakgi.view.SignUp.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.example.tacademy.ddakgi.R;

import io.chooco13.NotoTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFrag extends Fragment {

    RelativeLayout infoBt;
    CheckBox infoCheckBox;

    public InfoFrag() {
        // Required empty public constructor
    }

    // 약관 동의 버튼 클릭 시 버튼 색상 변경
    boolean flag = true;

    private View.OnClickListener InfoBtClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (flag) {
                // 이전 화면의 체크박스 체크!

                Log.i("infochecked", "true");
                // 버튼 색상 변경
                infoBt.setBackgroundColor(getResources().getColor(R.color.textpointColor));
                flag = false;
            } else {
                Log.i("infochecked", "false");
                // 이전 화면의 체크박스 체크 해제!

                // 버튼 색상 초기화
                infoBt.setBackgroundColor(getResources().getColor(R.color.subTextColor));
                flag = true;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_terms, container, false);

        View registerView = inflater.inflate(R.layout.activity_register_profile, container, false);
        infoCheckBox = (CheckBox) registerView.findViewById(R.id.infoCheckBox);
        /*infoBt = (RelativeLayout) view.findViewById(R.id.infoBt);
        infoBt.setOnClickListener(InfoBtClicklistener);*/

        NotoTextView terms = (NotoTextView) view.findViewById(R.id.infoText);
        terms.setText("<딱지>은(는) 개인정보보호법에 따라 이용자의 개인정보 보호 및 권익을 보호하고 개인정보와 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 처리방침을 두고 있습니다.\n" +
                "<딱지>은(는) 회사는 개인정보처리방침을 개정하는 경우 웹사이트 공지사항(또는 개별공지)을 통하여 공지할 것입니다.\n" +
                "○ 본 방침은부터 2017년 3월 13일부터 시행됩니다.\n" +
                "\n" +
                "1. 개인정보의 처리 목적 <딱지>은(는) 개인정보를 다음의 목적을 위해 처리합니다. 처리한 개인정보는 다음의 목적이외의 용도로는 사용되지 않으며 이용 목적이 변경될 시에는 사전동의를 구할 예정입니다.\n" +
                "가. 홈페이지 회원가입 및 관리\n" +
                "회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별•인증, 회원자격 유지•관리, 서비스 부정이용 방지, 각종 고지•통지, 고충처리, 분쟁 조정을 위한 기록 보존 등을 목적으로 개인정보를 처리합니다.\n" +
                "\n" +
                "나. 민원사무 처리\n" +
                "민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락•통지, 처리결과 통보 등을 목적으로 개인정보를 처리합니다.\n" +
                "\n" +
                "다. 재화 또는 서비스 제공\n" +
                "서비스 제공, 맞춤 서비스 제공, 본인인증, 연령인증 등을 목적으로 개인정보를 처리합니다.\n" +
                "\n" +
                "라. 마케팅 및 광고에의 활용\n" +
                "신규 서비스(제품) 개발 및 맞춤 서비스 제공, 이벤트 및 광고성 정보 제공 및 참여기회 제공 , 인구통계학적 특성에 따른 서비스 제공 및 광고 게재 , 서비스의 유효성 확인, 접속빈도 파악 또는 회원의 서비스 이용에 대한 통계 등을 목적으로 개인정보를 처리합니다.\n" +
                "\n" +
                "\n" +
                "2. 개인정보 파일 현황\n" +
                "1. 개인정보 파일명 : 딱지 회원정보\n" +
                "- 개인정보 항목 : 이메일, 휴대전화번호, 비밀번호, 성별, 생년월일, 이름, 주민등록번호, 서비스 이용 기록, 접속 로그, 쿠키, 접속 IP 정보, 결제기록, 프로필 사진, 생활패턴\n" +
                "- 수집방법 : 생성정보 수집 툴을 통한 수집, 어플리케이션\n" +
                "- 보유근거 : 맞춤형 서비스 제공 및 분쟁 예방\n" +
                "- 보유기간 : 3년\n" +
                "- 관련법령 : 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년, 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년\n" +
                "\n" +
                "\n" +
                "3. 개인정보처리 위탁\n" +
                "\n" +
                "① <딱지>은(는) 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.\n" +
                "② <딱지>은(는) 위탁계약 체결시 개인정보 보호법 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적․관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리․감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.\n" +
                "\n" +
                "③ 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.\n" +
                "\n" +
                "4. 정보주체의 권리,의무 및 그 행사방법 이용자는 개인정보주체로서 다음과 같은 권리를 행사할 수 있습니다.\n" +
                "① 정보주체는 딱지에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.\n" +
                "1. 개인정보 열람요구\n" +
                "2. 오류 등이 있을 경우 정정 요구\n" +
                "3. 삭제요구\n" +
                "4. 처리정지 요구\n" +
                "② 제1항에 따른 권리 행사는 딱지에 대해 개인정보 보호법 시행규칙 별지 제8호 서식에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 딱지은(는) 이에 대해 지체 없이 조치하겠습니다.\n" +
                "③ 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 딱지은(는) 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.\n" +
                "④ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.\n" +
                "\n" +
                "5. 처리하는 개인정보의 항목 작성 \n" +
                "\n" +
                "① <딱지>은(는) 다음의 개인정보 항목을 처리하고 있습니다.\n" +
                "1<홈페이지 회원가입 및 관리>\n" +
                "- 필수항목 : 이메일, 비밀번호, 성별, 생년월일, 이름, 닉네임, 생활패턴\n" +
                "\n" +
                "6. 개인정보의 파기<딱지>은(는) 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.\n" +
                "-파기절차이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에 따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.-파기기한이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.\n" +
                "-파기방법\n" +
                "전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다.\n" +
                "\n" +
                "7. 개인정보의 안전성 확보 조치 <딱지>은(는) 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적/관리적 및 물리적 조치를 하고 있습니다.\n" +
                "1. 개인정보의 암호화\n" +
                "이용자의 개인정보는 비밀번호는 암호화 되어 저장 및 관리되고 있어, 본인만이 알 수 있으며 중요한 데이터는 파일 및 전송 데이터를 암호화 하거나 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다.\n" +
                "\n" +
                "2. 접속기록의 보관 및 위변조 방지\n" +
                "개인정보처리시스템에 접속한 기록을 최소 6개월 이상 보관, 관리하고 있으며, 접속 기록이 위변조 및 도난, 분실되지 않도록 보안기능 사용하고 있습니다.\n" +
                "\n" +
                "3. 개인정보에 대한 접근 제한\n" +
                "개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.\n" +
                "\n" +
                "8. 개인정보 보호책임자 작성 \n" +
                "\n" +
                "① 딱지은(는) 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.\n" +
                "\n" +
                "▶ 개인정보 보호책임자 \n" +
                "성명 : 장용재\n" +
                "직책 : 서버 총 책임자\n" +
                "연락처 : ttakjiapp@gmail.com\n" +
                "② 정보주체께서는 딱지의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 딱지은(는) 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.\n" +
                "\n" +
                "9. 개인정보 처리방침 변경 \n" +
                "①이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.\n" +
                "\n");
        return view;
    }

}
