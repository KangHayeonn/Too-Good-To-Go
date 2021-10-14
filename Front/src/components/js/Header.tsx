import React from 'react'
import "../css/Header.css";

function Header(){
    return(
        <div>
            <nav>
                <div className ="wrap">
                    <div className="home">
                        {/*<Link to="/main">
                            <img src={navLogo} alt= "logo" />
                        </Link>*/}
                        <a href="">HOME</a>
                    </div>
                    <div className="account">
                        <a href="/my">마이페이지</a>
                        <a href="/login">로그인</a>
                    </div>
                </div>
            </nav>
            <div className = "title">
                <h1>Too Good To Go</h1>
            </div>
        </div>
    )
}

export default Header