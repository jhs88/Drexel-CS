  del *.cf *.vcd
  ghdl -a    alu1_sub_behavioral.vhd
  ghdl -a tb_alu1_sub_behavioral.vhd
  ghdl -e    alu1s
  ghdl -e tb_alu1sb
  ghdl -r tb_alu1sb --stop-time=200ns --disp-time --vcd=tb_alu1sb.vcd
  gtkwave tb_alu1sb.vcd
