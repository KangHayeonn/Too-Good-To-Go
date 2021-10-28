import React from 'react';
import '../css/Login.css';

function Login() {
	return (
		<div className="wrapper">
			<div className="container">
				<div className="title-ctn">
					<h4>로그인</h4>
					<img src="../image/Line 13.png" alt="" />
					<h4>SIGN IN</h4>
				</div>
				<div className="input-ctn">
					<input
						className="id input"
						type="text"
						name=""
						id=""
						placeholder="아이디를 입력하세요."
					/>
					<input
						className="password input"
						type="password"
						name=""
						id=""
						placeholder="비밀번호를 입력하세요."
					/>
				</div>
				<div className="btn-ctn">
					<button type="button" className="btn login-btn">
						로그인
					</button>
					<button type="button" className="btn register-btn">
						회원가입
					</button>
				</div>
			</div>
		</div>
	);
}

export default Login;
