-- MULTIPLEXER 4-1 STRUCTURAL

library IEEE;
use IEEE.std_logic_1164.all;

entity mux41s is
        port (
                x0: in STD_LOGIC;
                x1: in STD_LOGIC;
                x2: in STD_LOGIC;
                x3: in STD_LOGIC;
                s0: in STD_LOGIC;
                s1: in STD_LOGIC;
                z: out STD_LOGIC
        );
end mux41s;

architecture structural of mux41s is

  component and_3
     port(a,b,c : in std_logic;
          z     : out std_logic);
  end component;

  component or_4
     port(a,b,c,d : in std_logic;
          z      : out std_logic);
  end component;

  component not_1
     port(x : in std_logic;
          z : out std_logic);
  end component;

  signal s_n0, s_n1, s_a0, s_a1, s_a2, s_a3 : std_logic;

begin

A0 : and_3 port map( a => x0, b => s_n0, c => s_n1, z => s_a0);
A1 : and_3 port map( a => x1, b => s0,   c => s_n1, z => s_a1);
A2 : and_3 port map( a => x2, b => s_n0, c => s1,   z => s_a2);
A3 : and_3 port map( a => x3, b => s0,   c => s1,   z => s_a3);
N0 : not_1 port map( x => s0, z => s_n0);
N1 : not_1 port map( x => s1, z => s_n1);
O0 : or_4 port map( a => s_a0, b => s_a1, c => s_a2, d => s_a3, z => z);

end structural;

